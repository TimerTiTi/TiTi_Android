package com.titi.feature.time.ui.timer

import android.os.Build
import android.os.Bundle
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksState
import com.titi.core.util.addTimeToNow
import com.titi.core.util.getTodayDate
import com.titi.core.util.isAfterSixAM
import com.titi.doamin.daily.model.Daily
import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes
import com.titi.feature.time.SplashResultState

data class TimerUiState(
    val todayDate: String = getTodayDate(),
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val daily: Daily? = null,
) : MavericksState {

    constructor(args: Bundle) : this(
        recordTimes = getSplashResultStateFromArgs(args).recordTimes,
        timeColor = getSplashResultStateFromArgs(args).timeColor
    )

    val isDailyAfter6AM: Boolean = isAfterSixAM(daily?.day?.toString())
    val isSetTask: Boolean = recordTimes.currentTask != null
    val taskName : String = recordTimes.currentTask?.taskName ?: ""
    val timerColor = timeColor.toUiModel()
    val timerRecordTimes = recordTimes.toUiModel(daily)
    val isEnableStartRecording : Boolean = isDailyAfter6AM && isSetTask

}

fun getSplashResultStateFromArgs(args: Bundle): SplashResultState =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        args.getParcelable(
            Mavericks.KEY_ARG,
            SplashResultState::class.java
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
    isTextColorBlack = isTimerBlackTextColor
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

private fun RecordTimes.toUiModel(daily: Daily?) = TimerRecordTimes(
    outCircularProgress = (setTimerTime - savedTimerTime) / setTimerTime.toFloat(),
    inCircularProgress = savedSumTime / setGoalTime.toFloat(),
    savedSumTime = savedSumTime,
    savedTime = savedTimerTime,
    savedGoalTime = currentTask?.let {
        if (it.isTaskTargetTimeOn) {
            it.taskTargetTime - (daily?.tasks?.get(it.taskName) ?: 0)
        } else {
            savedGoalTime
        }
    } ?: savedGoalTime,
    finishGoalTime = addTimeToNow(
        currentTask?.let {
            if (it.isTaskTargetTimeOn) {
                it.taskTargetTime - (daily?.tasks?.get(it.taskName) ?: 0)
            } else {
                savedGoalTime
            }
        } ?: savedGoalTime
    ),
    isTaskTargetTimeOn = currentTask?.isTaskTargetTimeOn ?: false
)