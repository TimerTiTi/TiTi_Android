package com.titi.app.feature.measure.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.util.getMeasureTime
import com.titi.app.doamin.daily.usecase.AddMeasureTimeAtDailyUseCase
import com.titi.app.domain.alarm.usecase.CanSetAlarmUseCase
import com.titi.app.domain.alarm.usecase.CancelAlarmsUseCase
import com.titi.app.domain.alarm.usecase.SetStopWatchAlarmUseCase
import com.titi.app.domain.alarm.usecase.SetTimerAlarmUseCase
import com.titi.app.domain.task.usecase.AddMeasureTimeAtTaskUseCase
import com.titi.app.domain.time.model.RecordTimes
import com.titi.app.domain.time.usecase.AddMeasureTimeAtRecordTimesUseCase
import com.titi.app.feature.measure.model.MeasuringUiState
import com.titi.app.feature.measure.model.toMeasuringRecordTimes
import com.titi.domain.sleep.GetSleepModeFlowUseCase
import com.titi.domain.sleep.SetSleepModeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class MeasuringViewModel @AssistedInject constructor(
    @Assisted initialState: MeasuringUiState,
    private val addMeasureTimeAtDailyUseCase: AddMeasureTimeAtDailyUseCase,
    private val addMeasureTimeAtRecordTimesUseCase: AddMeasureTimeAtRecordTimesUseCase,
    private val addMeasureTimeAtTaskUseCase: AddMeasureTimeAtTaskUseCase,
    private val setSleepModeUseCase: SetSleepModeUseCase,
    private val canSetAlarmUseCase: CanSetAlarmUseCase,
    private val setTimerAlarmUseCase: SetTimerAlarmUseCase,
    private val setStopWatchAlarmUseCase: SetStopWatchAlarmUseCase,
    private val cancelAlarmsUseCase: CancelAlarmsUseCase,
    getSleepModeFlowUseCase: GetSleepModeFlowUseCase,
) : MavericksViewModel<MeasuringUiState>(initialState) {

    private val _onFinish: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val onFinish = _onFinish.asSharedFlow()

    init {
        getSleepModeFlowUseCase().catch {
            Log.e("MeasuringViewModel", it.message.toString())
        }.setOnEach {
            copy(
                measuringRecordTimes =
                recordTimes.toMeasuringRecordTimes(
                    isSleepMode = it,
                    measureTime = measureTime,
                    daily = daily,
                ),
                isSleepMode = it,
            )
        }

        viewModelScope.launch {
            while (true) {
                delay(1000)
                setState {
                    copy(
                        measuringRecordTimes = recordTimes.toMeasuringRecordTimes(
                            isSleepMode = isSleepMode,
                            daily = daily,
                            measureTime = getMeasureTime(
                                recordTimes.recordStartAt
                                    ?: ZonedDateTime.now(ZoneOffset.UTC).toString(),
                            ),
                        ),
                        measureTime = getMeasureTime(
                            recordTimes.recordStartAt
                                ?: ZonedDateTime.now(ZoneOffset.UTC).toString(),
                        ),
                    )
                }
            }
        }
    }

    fun canSetAlarm() = canSetAlarmUseCase()

    fun setAlarm(
        title: String,
        finishMessage: String,
        fiveMinutesBeforeFinish: String?,
        measureTime: Long,
    ) {
        viewModelScope.launch {
            if (fiveMinutesBeforeFinish != null) {
                setTimerAlarmUseCase(
                    title = title,
                    finishMessage = finishMessage,
                    fiveMinutesBeforeFinish = fiveMinutesBeforeFinish,
                    measureTime = measureTime,
                )
            } else {
                setStopWatchAlarmUseCase(
                    title = title,
                    finishMessage = finishMessage,
                    measureTime = measureTime,
                )
            }
        }
    }

    fun setSleepMode(isSleepMode: Boolean) {
        viewModelScope.launch {
            setSleepModeUseCase(isSleepMode)
        }
    }

    fun stopMeasuring(recordTimes: RecordTimes, measureTime: Long, endTime: String) {
        viewModelScope.launch {
            val jobs = mutableListOf<Job>()
            val taskName = recordTimes.currentTask?.taskName
            val startTime =
                recordTimes.recordStartAt ?: ZonedDateTime.now(ZoneOffset.UTC).toString()

            if (taskName != null) {
                jobs.add(
                    launch {
                        addMeasureTimeAtDailyUseCase(
                            taskName = taskName,
                            startTime = startTime,
                            endTime = endTime,
                        )
                    },
                )

                jobs.add(
                    launch {
                        addMeasureTimeAtRecordTimesUseCase(
                            recordTimes = recordTimes,
                            measureTime = measureTime,
                        )
                    },
                )

                jobs.add(
                    launch {
                        addMeasureTimeAtTaskUseCase(
                            taskName = taskName,
                            measureTime = measureTime,
                        )
                    },
                )
            }

            jobs.add(
                launch { cancelAlarmsUseCase() },
            )

            jobs.joinAll()
        }.invokeOnCompletion {
            viewModelScope.launch {
                _onFinish.emit(true)
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MeasuringViewModel, MeasuringUiState> {
        override fun create(state: MeasuringUiState): MeasuringViewModel
    }

    companion object :
        MavericksViewModelFactory<MeasuringViewModel, MeasuringUiState>
        by hiltMavericksViewModelFactory()
}
