package com.titi.app.feature.time.ui.timer

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.doamin.daily.usecase.AddDailyUseCase
import com.titi.app.doamin.daily.usecase.GetCurrentDailyFlowUseCase
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.domain.color.usecase.UpdateColorUseCase
import com.titi.app.domain.time.model.RecordTimes
import com.titi.app.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.app.domain.time.usecase.UpdateMeasuringStateUseCase
import com.titi.app.domain.time.usecase.UpdateRecordingModeUseCase
import com.titi.app.domain.time.usecase.UpdateSetGoalTimeUseCase
import com.titi.app.domain.time.usecase.UpdateSetTimerTimeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TimerViewModel @AssistedInject constructor(
    @Assisted initialState: TimerUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    getCurrentDailyFlowUseCase: GetCurrentDailyFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
    private val updateSetGoalTimeUseCase: UpdateSetGoalTimeUseCase,
    private val addDailyUseCase: AddDailyUseCase,
    private val updateMeasuringStateUseCase: UpdateMeasuringStateUseCase,
    private val updateSetTimerTimeUseCase: UpdateSetTimerTimeUseCase,
) : MavericksViewModel<TimerUiState>(initialState) {

    private lateinit var prevTimerColor: TimerColor

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
            updateRecordingModeUseCase(1)
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
            if (::prevTimerColor.isInitialized) {
                updateColorUseCase(
                    recordingMode = 1,
                    backgroundColor = prevTimerColor.backgroundColor,
                    isTextColorBlack = prevTimerColor.isTextColorBlack
                )
            }
        }
    }

    fun savePrevTimerColor(timerColor: TimerColor) {
        prevTimerColor = timerColor
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

    fun updateSetTimerTime(
        recordTimes: RecordTimes,
        timerTime: Long,
    ) {
        viewModelScope.launch {
            updateSetTimerTimeUseCase(
                recordTimes,
                timerTime
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TimerViewModel, TimerUiState> {
        override fun create(state: TimerUiState): TimerViewModel
    }

    companion object :
        MavericksViewModelFactory<TimerViewModel, TimerUiState> by hiltMavericksViewModelFactory()


}

