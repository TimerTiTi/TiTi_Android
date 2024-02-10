package com.titi.app.feature.time.model

import android.os.Build
import android.os.Bundle
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksState
import com.titi.app.core.util.addTimeToNow
import com.titi.app.core.util.getTodayDate
import com.titi.app.core.util.isAfterSixAM
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.time.model.RecordTimes

data class TimerUiState(
    val todayDate: String = getTodayDate(),
    val recordTimes: RecordTimes,
    val timeColor: TimeColor,
    val daily: Daily?,
) : MavericksState {
    constructor(args: Bundle) : this(
        recordTimes = getSplashResultStateFromArgs(args).recordTimes,
        timeColor = getSplashResultStateFromArgs(args).timeColor,
        daily = getSplashResultStateFromArgs(args).daily,
    )

    val isDailyAfter6AM: Boolean = isAfterSixAM(daily?.day)
    val isSetTask: Boolean = recordTimes.currentTask != null
    val taskName: String = recordTimes.currentTask?.taskName ?: ""
    val timerColor = timeColor.toUiModel()
    val timerRecordTimes = recordTimes.toUiModel(daily)
    val isEnableStartRecording: Boolean = isDailyAfter6AM && isSetTask
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

private fun RecordTimes.toUiModel(daily: Daily?): TimerRecordTimes {
    val goalTime =
        currentTask?.let {
            if (it.isTaskTargetTimeOn) {
                it.taskTargetTime - (daily?.tasks?.get(it.taskName) ?: 0)
            } else {
                savedGoalTime
            }
        } ?: savedGoalTime

    return TimerRecordTimes(
        outCircularProgress = (setTimerTime - savedTimerTime) / setTimerTime.toFloat(),
        inCircularProgress =
        currentTask?.let {
            if (it.isTaskTargetTimeOn) {
                val taskTime = daily?.tasks?.get(it.taskName) ?: 0
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
