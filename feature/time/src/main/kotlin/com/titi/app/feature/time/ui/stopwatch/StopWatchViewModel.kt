package com.titi.app.feature.time.ui.stopwatch

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.util.isAfterSixAM
import com.titi.app.core.util.toJson
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.usecase.AddDailyUseCase
import com.titi.app.doamin.daily.usecase.GetLastDailyFlowUseCase
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.domain.color.usecase.UpdateColorUseCase
import com.titi.app.domain.time.model.RecordTimes
import com.titi.app.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.app.domain.time.usecase.UpdateMeasuringStateUseCase
import com.titi.app.domain.time.usecase.UpdateRecordingModeUseCase
import com.titi.app.domain.time.usecase.UpdateSavedStopWatchTimeUseCase
import com.titi.app.domain.time.usecase.UpdateSetGoalTimeUseCase
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.model.StopWatchColor
import com.titi.app.feature.time.model.StopWatchUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class StopWatchViewModel @AssistedInject constructor(
    @Assisted initialState: StopWatchUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    getLastDailyFlowUseCase: GetLastDailyFlowUseCase,
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

        getLastDailyFlowUseCase().catch {
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
                recordingMode = 2,
                isTextColorBlack = isTextBlackColor,
            )
        }
    }

    fun rollBackTimerColor() {
        viewModelScope.launch {
            if (::prevStopWatchColor.isInitialized) {
                updateColorUseCase(
                    recordingMode = 2,
                    backgroundColor = prevStopWatchColor.backgroundColor,
                    isTextColorBlack = prevStopWatchColor.isTextColorBlack,
                )
            }
        }
    }

    fun savePrevTimerColor(stopWatchColor: StopWatchColor) {
        prevStopWatchColor = stopWatchColor
    }

    fun updateSetGoalTime(recordTimes: RecordTimes, setGoalTime: Long) {
        viewModelScope.launch {
            updateSetGoalTimeUseCase(
                recordTimes,
                setGoalTime,
            )
        }
    }

    fun startRecording(recordTimes: RecordTimes, daily: Daily?, timeColor: TimeColor): String {
        val updateRecordTimes = if (isAfterSixAM(daily?.day)) {
            if (recordTimes.savedTimerTime <= 0) {
                recordTimes.copy(
                    recording = true,
                    recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                    savedTimerTime = recordTimes.setTimerTime,
                )
            } else {
                recordTimes.copy(
                    recording = true,
                    recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                )
            }
        } else {
            recordTimes.copy(
                recording = true,
                recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                savedSumTime = 0,
                savedTimerTime = recordTimes.setTimerTime,
                savedStopWatchTime = 0,
            )
        }

        val updateDaily = if (daily != null && isAfterSixAM(daily.day)) {
            daily
        } else {
            Daily()
        }

        viewModelScope.launch {
            updateMeasuringStateUseCase(updateRecordTimes)
            addDailyUseCase(updateDaily)
        }

        return SplashResultState(
            recordTimes = updateRecordTimes,
            daily = updateDaily,
            timeColor = timeColor,
        ).toJson()
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
        MavericksViewModelFactory<StopWatchViewModel, StopWatchUiState>
        by hiltMavericksViewModelFactory()
}
