package com.titi.domain.color.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.domain.color.model.BackgroundColors
import com.titi.domain.color.model.TimeColor

internal fun ColorRepositoryModel.toDomain() = TimeColor(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor
)

internal fun BackgroundColorRepositoryModel.toDomain() = BackgroundColors(
    colors = backgroundColors
)