package com.titi.feature.time.ui.stopwatch

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.doamin.daily.usecase.AddDailyUseCase
import com.titi.doamin.daily.usecase.GetCurrentDailyFlowUseCase
import com.titi.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.domain.color.usecase.UpdateColorUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.domain.time.usecase.UpdateMeasuringStateUseCase
import com.titi.domain.time.usecase.UpdateRecordingModeUseCase
import com.titi.domain.time.usecase.UpdateSavedStopWatchTimeUseCase
import com.titi.domain.time.usecase.UpdateSetGoalTimeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StopWatchViewModel @AssistedInject constructor(
    @Assisted initialState: StopWatchUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    getCurrentDailyFlowUseCase: GetCurrentDailyFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
    private val updateSetGoalTimeUseCase: UpdateSetGoalTimeUseCase,
    private val addDailyUseCase: AddDailyUseCase,
    private val updateMeasuringStateUseCase: UpdateMeasuringStateUseCase,
    private val updateSavedStopWatchTimeUseCase: UpdateSavedStopWatchTimeUseCase,
) : MavericksViewModel<StopWatchUiState>(initialState) {

    private lateinit var prevStopWatchColor: StopWatchColor

    init {
        getRecordTimesFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(recordTimes = it)
        }

        getTimeColorFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(timeColor = it)
        }

        getCurrentDailyFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(daily = it)
        }
    }

    fun updateRecordingMode() {
        viewModelScope.launch {
            updateRecordingModeUseCase(2)
        }
    }

    fun updateColor(isTextBlackColor: Boolean = false) {
        viewModelScope.launch {
            updateColorUseCase(
                recordingMode = 1,
                isTextColorBlack = isTextBlackColor
            )
        }
    }

    fun rollBackTimerColor() {
        viewModelScope.launch {
            if (::prevStopWatchColor.isInitialized) {
                updateColorUseCase(
                    recordingMode = 1,
                    backgroundColor = prevStopWatchColor.backgroundColor,
                    isTextColorBlack = prevStopWatchColor.isTextColorBlack
                )
            }
        }
    }

    fun savePrevTimerColor(stopWatchColor: StopWatchColor) {
        prevStopWatchColor = stopWatchColor
    }

    fun updateSetGoalTime(
        recordTimes: RecordTimes,
        setGoalTime: Long
    ) {
        viewModelScope.launch {
            updateSetGoalTimeUseCase(
                recordTimes,
                setGoalTime
            )
        }
    }

    fun addDaily() {
        viewModelScope.launch {
            addDailyUseCase()
        }
    }

    fun updateMeasuringState(recordTimes: RecordTimes) {
        viewModelScope.launch {
            updateMeasuringStateUseCase(recordTimes)
        }
    }

    fun updateSavedStopWatchTime(recordTimes: RecordTimes) {
        viewModelScope.launch {
            updateSavedStopWatchTimeUseCase(recordTimes)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<StopWatchViewModel, StopWatchUiState> {
        override fun create(state: StopWatchUiState): StopWatchViewModel
    }

    companion object :
        MavericksViewModelFactory<StopWatchViewModel, StopWatchUiState> by hiltMavericksViewModelFactory()

}