package com.titi.feature.measure.ui

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.doamin.daily.usecase.AddMeasureTimeAtDailyUseCase
import com.titi.domain.sleep.GetSleepModeFlowUseCase
import com.titi.domain.sleep.SetSleepModeUseCase
import com.titi.domain.task.usecase.AddMeasureTimeAtTaskUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.AddMeasureTimeAtRecordTimesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class MeasuringViewModel @AssistedInject constructor(
    @Assisted initialState: MeasuringUiState,
    private val addMeasureTimeAtDailyUseCase: AddMeasureTimeAtDailyUseCase,
    private val addMeasureTimeAtRecordTimesUseCase: AddMeasureTimeAtRecordTimesUseCase,
    private val addMeasureTimeAtTaskUseCase: AddMeasureTimeAtTaskUseCase,
    getSleepModeFlowUseCase: GetSleepModeFlowUseCase,
    private val setSleepModeUseCase: SetSleepModeUseCase
) : MavericksViewModel<MeasuringUiState>(initialState) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                setState { copy(measureTime = measureTime + 1) }
            }
        }

        getSleepModeFlowUseCase().catch {
            Log.e("MeasuringViewModel", it.message.toString())
        }.setOnEach {
            copy(isSleepMode = it)
        }
    }

    fun setSleepMode(isSleepMode: Boolean) {
        viewModelScope.launch {
            setSleepModeUseCase(isSleepMode)
        }
    }

    fun stopMeasuring(
        recordTimes: RecordTimes,
        measureTime: Long,
        endTime: String
    ) {
        viewModelScope.launch {
            val taskName = recordTimes.currentTask?.taskName
            val startTime =
                recordTimes.recordStartAt ?: ZonedDateTime.now(ZoneOffset.UTC).toString()

            if (taskName != null) {
                launch {
                    addMeasureTimeAtDailyUseCase(
                        taskName = taskName,
                        startTime = startTime,
                        endTime = endTime,
                        measureTime = measureTime
                    )
                }

                launch {
                    addMeasureTimeAtRecordTimesUseCase(
                        recordTimes = recordTimes,
                        measureTime = measureTime
                    )
                }

                launch {
                    addMeasureTimeAtTaskUseCase(
                        taskName = taskName,
                        measureTime = measureTime
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MeasuringViewModel, MeasuringUiState> {
        override fun create(state: MeasuringUiState): MeasuringViewModel
    }

    companion object :
        MavericksViewModelFactory<MeasuringViewModel, MeasuringUiState> by hiltMavericksViewModelFactory()

}