package com.titi.app.feature.time.model

import android.os.Bundle
import com.airbnb.mvrx.MavericksState
import com.titi.app.core.util.addTimeToNow
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.time.model.RecordTimes

data class StopWatchUiState(
    val recordTimes: RecordTimes,
    val timeColor: TimeColor,
    val daily: Daily,
    val splashResultStateString: String? = null,
    val showResetDailySnackBar: Boolean = false,
) : MavericksState {
    constructor(args: Bundle) : this(
        recordTimes = getSplashResultStateFromArgs(args).recordTimes,
        timeColor = getSplashResultStateFromArgs(args).timeColor,
        daily = getSplashResultStateFromArgs(args).daily,
    )

    val isSetTask: Boolean = recordTimes.currentTask != null
    val taskName: String = recordTimes.currentTask?.taskName ?: ""
    val stopWatchColor = timeColor.toUiModel()
    val stopWatchRecordTimes = recordTimes.toUiModel(daily)
}

data class StopWatchColor(
    val backgroundColor: Long,
    val isTextColorBlack: Boolean,
)

private fun TimeColor.toUiModel() = StopWatchColor(
    backgroundColor = stopwatchBackgroundColor,
    isTextColorBlack = isStopwatchBlackTextColor,
)

data class StopWatchRecordTimes(
    val outCircularProgress: Float,
    val inCircularProgress: Float,
    val savedSumTime: Long,
    val savedTime: Long,
    val savedGoalTime: Long,
    val finishGoalTime: String,
    val isTaskTargetTimeOn: Boolean,
)

private fun RecordTimes.toUiModel(daily: Daily): StopWatchRecordTimes {
    val goalTime = currentTask?.let {
        if (it.isTaskTargetTimeOn) {
            it.taskTargetTime - (daily.tasks?.get(it.taskName) ?: 0)
        } else {
            savedGoalTime
        }
    } ?: savedGoalTime

    return StopWatchRecordTimes(
        outCircularProgress = savedStopWatchTime / 3600f,
        inCircularProgress =
        currentTask?.let {
            if (it.isTaskTargetTimeOn) {
                val taskTime = daily.tasks?.get(it.taskName) ?: 0
                taskTime / it.taskTargetTime.toFloat()
            } else {
                savedSumTime / setGoalTime.toFloat()
            }
        } ?: (savedSumTime / setGoalTime.toFloat()),
        savedSumTime = savedSumTime,
        savedTime = savedStopWatchTime,
        savedGoalTime = goalTime,
        finishGoalTime = addTimeToNow(goalTime),
        isTaskTargetTimeOn = currentTask?.isTaskTargetTimeOn ?: false,
    )
}
