package com.titi.domain.color.mapper

import com.titi.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.domain.color.model.BackgroundColors
import com.titi.domain.color.model.TimeColor

internal fun TimeColor.toRepository() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor
)

internal fun BackgroundColors.toRepository() = BackgroundColorRepositoryModel(
    backgroundColors = colors
)