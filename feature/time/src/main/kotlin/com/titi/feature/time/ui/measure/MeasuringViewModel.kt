package com.titi.feature.time.ui.measure

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.core.util.getMeasureTime
import com.titi.doamin.daily.usecase.AddMeasureTimeAtDailyUseCase
import com.titi.domain.task.usecase.AddMeasureTimeAtTaskUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.AddMeasureTimeAtRecordTimesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime

data class MeasuringUiState(
    val isSleepMode: Boolean = false,
    val measureTime: Long = 0,
) : MavericksState {
    constructor(startTime: String) : this(measureTime = getMeasureTime(startTime))
}

class MeasuringViewModel @AssistedInject constructor(
    @Assisted initialState: MeasuringUiState,
    private val addMeasureTimeAtDailyUseCase: AddMeasureTimeAtDailyUseCase,
    private val addMeasureTimeAtRecordTimesUseCase: AddMeasureTimeAtRecordTimesUseCase,
    private val addMeasureTimeAtTaskUseCase: AddMeasureTimeAtTaskUseCase
) : MavericksViewModel<MeasuringUiState>(initialState) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                setState { copy(measureTime = measureTime + 1) }
            }
        }
    }

    fun updateSleepMode() {
        setState {
            copy(isSleepMode = !isSleepMode)
        }
    }

    fun stopMeasuring(
        recordTimes: RecordTimes,
        measureTime: Long,
        endTime: LocalDateTime
    ) {
        viewModelScope.launch {
            val taskName = recordTimes.recordTask
            val startTime = recordTimes.recordStartAt

            if (taskName != null && startTime != null) {
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