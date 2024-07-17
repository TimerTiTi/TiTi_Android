package com.titi.app.feature.main.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titi.app.core.util.getMeasureTime
import com.titi.app.doamin.daily.usecase.AddMeasureTimeAtDailyUseCase
import com.titi.app.doamin.daily.usecase.GetTodayDailyUseCase
import com.titi.app.domain.color.usecase.GetTimeColorUseCase
import com.titi.app.domain.task.usecase.AddMeasureTimeAtTaskUseCase
import com.titi.app.domain.time.usecase.AddMeasureTimeAtRecordTimesUseCase
import com.titi.app.domain.time.usecase.GetRecordTimesUseCase
import com.titi.app.feature.main.model.SplashResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    getRecordTimesUseCase: GetRecordTimesUseCase,
    getTimeColorUseCase: GetTimeColorUseCase,
    getTodayDailyUseCase: GetTodayDailyUseCase,
    private val addMeasureTimeAtDailyUseCase: AddMeasureTimeAtDailyUseCase,
    private val addMeasureTimeAtRecordTimesUseCase: AddMeasureTimeAtRecordTimesUseCase,
    private val addMeasureTimeAtTaskUseCase: AddMeasureTimeAtTaskUseCase,
) : ViewModel() {
    var splashResultState: SplashResultState? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            val recordTimes = getRecordTimesUseCase()
            val timeColor = getTimeColorUseCase()
            val daily = getTodayDailyUseCase()

            val taskName = recordTimes.currentTask?.taskName
            val startTime = recordTimes.recordStartAt
                ?: ZonedDateTime.now(ZoneOffset.UTC).toString()
            val endTime = ZonedDateTime.now(ZoneOffset.UTC).toString()
            val measureTime = getMeasureTime(startTime)

            splashResultState = if (
                taskName != null &&
                recordTimes.recording &&
                recordTimes.recordingMode == 1 &&
                recordTimes.savedTimerTime - measureTime <= 0L
            ) {
                val dailyJob = launch {
                    addMeasureTimeAtDailyUseCase(
                        taskName = taskName,
                        startTime = startTime,
                        endTime = endTime,
                    )
                }

                val recordTimesJob = launch {
                    addMeasureTimeAtRecordTimesUseCase(
                        recordTimes = recordTimes,
                        measureTime = measureTime,
                    )
                }

                val taskJob = launch {
                    addMeasureTimeAtTaskUseCase(
                        taskName = taskName,
                        measureTime = measureTime,
                    )
                }

                joinAll(dailyJob, recordTimesJob, taskJob)

                val updateRecordTimes = getRecordTimesUseCase()
                val updateTimeColor = getTimeColorUseCase()
                val updateDaily = getTodayDailyUseCase()

                SplashResultState(
                    recordTimes = updateRecordTimes,
                    timeColor = updateTimeColor,
                    daily = updateDaily,
                    isMeasureFinish = true,
                )
            } else {
                SplashResultState(
                    recordTimes = recordTimes,
                    timeColor = timeColor,
                    daily = daily,
                )
            }
        }
    }
}
