package com.titi.app.feature.time.ui.stopwatch

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.util.isAfterH
import com.titi.app.core.util.toJson
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.usecase.GetResetDailyEventUseCase
import com.titi.app.doamin.daily.usecase.GetTodayDailyFlowUseCase
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.domain.color.usecase.UpdateColorUseCase
import com.titi.app.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.app.domain.time.usecase.UpdateRecordTimesUseCase
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class StopWatchViewModel @AssistedInject constructor(
    @Assisted initialState: StopWatchUiState,
    private val getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    private val getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    private val getTodayDailyFlowUseCase: GetTodayDailyFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
    private val updateSetGoalTimeUseCase: UpdateSetGoalTimeUseCase,
    private val updateRecordTimesUseCase: UpdateRecordTimesUseCase,
    private val updateSavedStopWatchTimeUseCase: UpdateSavedStopWatchTimeUseCase,
    private val getResetDailyEventUseCase: GetResetDailyEventUseCase,
) : MavericksViewModel<StopWatchUiState>(initialState) {

    private lateinit var prevStopWatchColor: StopWatchColor

    fun init() {
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

        getTodayDailyFlowUseCase()
            .distinctUntilChanged()
            .catch {
                Log.e("TimeViewModel", it.message.toString())
            }.setOnEach {
                copy(daily = it)
            }
    }

    fun updateDailyRecordTimesAfterH() {
        withState {
            viewModelScope.launch {
                if (getResetDailyEventUseCase()) {
                    updateRecordTimesUseCase(
                        recordTimes = it.recordTimes.copy(
                            recordingMode = 2,
                            savedSumTime = 0,
                            savedTimerTime = it.recordTimes.setTimerTime,
                            savedStopWatchTime = 0,
                            savedGoalTime = it.recordTimes.setGoalTime,
                        ),
                    )

                    setState {
                        copy(
                            daily = Daily(),
                            showResetDailySnackBar = true,
                        )
                    }
                } else {
                    updateRecordingModeUseCase(2)
                }
            }
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

    fun updateSetGoalTime(setGoalTime: Long) {
        withState {
            viewModelScope.launch {
                updateSetGoalTimeUseCase(
                    it.recordTimes,
                    setGoalTime,
                )
            }
        }
    }

    fun startRecording() {
        withState {
            val isAfterH = it.daily.day.isAfterH(6)
            val updatePair = if (isAfterH) {
                it.recordTimes.copy(
                    recording = true,
                    recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                    savedSumTime = 0,
                    savedTimerTime = it.recordTimes.setTimerTime,
                    savedStopWatchTime = 0,
                    savedGoalTime = it.recordTimes.setGoalTime,
                ) to Daily()
            } else {
                it.recordTimes.copy(
                    recording = true,
                    recordStartAt = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                ) to it.daily
            }

            viewModelScope.launch {
                updateRecordTimesUseCase(updatePair.first)
            }

            setState {
                copy(
                    splashResultStateString = SplashResultState(
                        recordTimes = updatePair.first,
                        daily = updatePair.second,
                        timeColor = it.timeColor,
                    ).toJson(),
                    showResetDailySnackBar = isAfterH,
                )
            }
        }
    }

    fun updateSavedStopWatchTime() {
        withState {
            viewModelScope.launch {
                updateSavedStopWatchTimeUseCase(it.recordTimes)
            }
        }
    }

    fun initSplashResultStateString() {
        setState {
            copy(splashResultStateString = null)
        }
    }

    fun initShowResetDailySnackBar() {
        setState {
            copy(showResetDailySnackBar = false)
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
