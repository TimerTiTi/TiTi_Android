package com.titi.app.feature.time.ui.stopwatch

import android.util.Log
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.core.util.isAfterH
import com.titi.app.core.util.toJson
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.usecase.GetTodayDailyFlowUseCase
import com.titi.app.doamin.daily.usecase.UpsertDailyUseCase
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class StopWatchViewModel @AssistedInject constructor(
    @Assisted initialState: StopWatchUiState,
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    getTodayDailyFlowUseCase: GetTodayDailyFlowUseCase,
    private val updateRecordingModeUseCase: UpdateRecordingModeUseCase,
    private val updateColorUseCase: UpdateColorUseCase,
    private val updateSetGoalTimeUseCase: UpdateSetGoalTimeUseCase,
    private val upsertDailyUseCase: UpsertDailyUseCase,
    private val updateRecordTimesUseCase: UpdateRecordTimesUseCase,
    private val updateSavedStopWatchTimeUseCase: UpdateSavedStopWatchTimeUseCase,
) : MavericksViewModel<StopWatchUiState>(initialState) {

    private val _splashResultStateString: MutableStateFlow<String?> = MutableStateFlow(null)
    val splashResultStateString = _splashResultStateString.asStateFlow()

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

        getTodayDailyFlowUseCase().catch {
            Log.e("TimeViewModel", it.message.toString())
        }.setOnEach {
            copy(daily = it)
        }
    }

    fun updateDailyRecordTimesAfterH(hour: Int) {
        withState {
            if (it.daily.day.isAfterH(hour)) {
                viewModelScope.launch {
                    updateRecordTimesUseCase(
                        recordTimes = it.recordTimes.copy(
                            savedSumTime = 0,
                            savedTimerTime = it.recordTimes.setTimerTime,
                            savedStopWatchTime = 0,
                            savedGoalTime = it.recordTimes.setGoalTime,
                        ),
                    )
                }

                setState {
                    copy(daily = Daily())
                }
            }
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
            val updatePair = if (it.daily.day.isAfterH(6)) {
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
                upsertDailyUseCase(updatePair.second)
            }

            _splashResultStateString.value = SplashResultState(
                recordTimes = updatePair.first,
                daily = updatePair.second,
                timeColor = it.timeColor,
            ).toJson()
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
        _splashResultStateString.value = null
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<StopWatchViewModel, StopWatchUiState> {
        override fun create(state: StopWatchUiState): StopWatchViewModel
    }

    companion object :
        MavericksViewModelFactory<StopWatchViewModel, StopWatchUiState>
        by hiltMavericksViewModelFactory()
}
