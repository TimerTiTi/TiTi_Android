package com.titi.data.color.impl.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.data.color.impl.local.model.BackgroundColorEntity
import com.titi.data.color.impl.local.model.ColorEntity

internal fun ColorEntity.toRepositoryModel() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor
)

internal fun BackgroundColorEntity.toRepositoryModel() = BackgroundColorRepositoryModel(
    backgroundColors = backgroundColors
)