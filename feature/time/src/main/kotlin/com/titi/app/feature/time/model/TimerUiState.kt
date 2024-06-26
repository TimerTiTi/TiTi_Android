package com.titi.app.feature.time.model

import android.os.Build
import android.os.Bundle
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksState
import com.titi.app.core.util.addTimeToNow
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.time.model.RecordTimes

data class TimerUiState(
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
    val timerColor = timeColor.toUiModel()
    val timerRecordTimes = recordTimes.toUiModel(daily)
}

fun getSplashResultStateFromArgs(args: Bundle): SplashResultState =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        args.getParcelable(
            Mavericks.KEY_ARG,
            SplashResultState::class.java,
        )
    } else {
        args.getParcelable(Mavericks.KEY_ARG)
    } ?: SplashResultState()

data class TimerColor(
    val backgroundColor: Long,
    val isTextColorBlack: Boolean,
)

private fun TimeColor.toUiModel() = TimerColor(
    backgroundColor = timerBackgroundColor,
    isTextColorBlack = isTimerBlackTextColor,
)

data class TimerRecordTimes(
    val outCircularProgress: Float,
    val inCircularProgress: Float,
    val savedSumTime: Long,
    val savedTime: Long,
    val savedGoalTime: Long,
    val finishGoalTime: String,
    val isTaskTargetTimeOn: Boolean,
)

private fun RecordTimes.toUiModel(daily: Daily): TimerRecordTimes {
    val goalTime = currentTask?.let {
        if (it.isTaskTargetTimeOn) {
            it.taskTargetTime - (daily.tasks?.get(it.taskName) ?: 0)
        } else {
            savedGoalTime
        }
    } ?: savedGoalTime

    return TimerRecordTimes(
        outCircularProgress = (setTimerTime - savedTimerTime) / setTimerTime.toFloat(),
        inCircularProgress = currentTask?.let {
            if (it.isTaskTargetTimeOn) {
                val taskTime = daily.tasks?.get(it.taskName) ?: 0
                taskTime / it.taskTargetTime.toFloat()
            } else {
                savedSumTime / setGoalTime.toFloat()
            }
        } ?: (savedSumTime / setGoalTime.toFloat()),
        savedSumTime = savedSumTime,
        savedTime = savedTimerTime,
        savedGoalTime = goalTime,
        finishGoalTime = addTimeToNow(goalTime),
        isTaskTargetTimeOn = currentTask?.isTaskTargetTimeOn ?: false,
    )
}
