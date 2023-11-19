package com.titi.feature.time.ui.time

import android.util.Log
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.core.util.getTodayDate
import com.titi.core.util.isAfterSixAM
import com.titi.doamin.daily.model.Daily
import com.titi.doamin.daily.usecase.AddDailyUseCase
import com.titi.doamin.daily.usecase.GetCurrentDailyUseCase
import com.titi.domain.color.model.TimeColor
import com.titi.domain.color.usecase.GetColorUseCase
import com.titi.domain.color.usecase.UpdateColorUseCase
import com.titi.domain.time.model.RecordTimes
import com.titi.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.domain.time.usecase.UpdateMeasuringStateUseCase
import com.titi.domain.time.usecase.UpdateRecordingModeUseCase
import com.titi.domain.time.usecase.UpdateSavedStopWatchTimeUseCase
import com.titi.domain.time.usecase.UpdateSavedTimerTimeUseCase
import com.titi.domain.time.usecase.UpdateSetGoalTimeUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class TimeUiState(
    val todayDate: String = "",
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val daily: Daily? = null,
) : MavericksState {
    val isDailyAfter6AM: Boolean = isAfterSixAM(daily?.day?.toString())
    val isSetTask: Boolean = recordTimes.recordTask != null
}

class TimeViewModel @AssistedInject constructor(
    @Assisted initialState: TimeUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    getColorUseCase: GetColorUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
    private val updateSetGoalTimeUseCase: UpdateSetGoalTimeUseCase,
    private val addDailyUseCase: AddDailyUseCase,
    getCurrentDailyUseCase: GetCurrentDailyUseCase,
    private val updateSavedTimerTimeUseCase: UpdateSavedTimerTimeUseCase,
    private val updateSavedStopWatchTimeUseCase: UpdateSavedStopWatchTimeUseCase,
    private val updateMeasuringStateUseCase: UpdateMeasuringStateUseCase
) : MavericksViewModel<TimeUiState>(initialState) {

    init {
        setState {
            copy(todayDate = getTodayDate())
        }

        getRecordTimesFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(recordTimes = it)
        }

        getColorUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(timeColor = it)
        }

        getCurrentDailyUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(daily = it)
        }
    }

    private lateinit var prevTimeColor: TimeColor

    fun updateRecordingMode(recordingMode: Int) {
        viewModelScope.launch {
            updateRecordingModeUseCase(recordingMode)
        }
    }

    fun updateColor(timeColor: TimeColor) {
        viewModelScope.launch {
            updateColorUseCase(timeColor = timeColor)
        }
    }

    fun savePrevTimeColor(timeColor: TimeColor) {
        prevTimeColor = timeColor
    }

    fun rollBackTimeColor() {
        viewModelScope.launch {
            if (::prevTimeColor.isInitialized) {
                updateColorUseCase(timeColor = prevTimeColor)
            }
        }
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

    fun updateSavedTimerTime(
        recordTimes: RecordTimes,
        timerTime: Long,
    ) {
        viewModelScope.launch {
            updateSavedTimerTimeUseCase(
                recordTimes,
                timerTime
            )
        }
    }

    fun updateSavedStopWatchTime(recordTimes: RecordTimes) {
        viewModelScope.launch {
            updateSavedStopWatchTimeUseCase(recordTimes)
        }
    }

    fun updateMeasuringState(recordTimes: RecordTimes) {
        viewModelScope.launch {
            updateMeasuringStateUseCase(recordTimes)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<TimeViewModel, TimeUiState> {
        override fun create(state: TimeUiState): TimeViewModel
    }

    companion object :
        MavericksViewModelFactory<TimeViewModel, TimeUiState> by hiltMavericksViewModelFactory()

}