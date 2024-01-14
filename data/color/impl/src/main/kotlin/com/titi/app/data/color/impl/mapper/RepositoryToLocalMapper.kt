package com.titi.app.data.color.impl.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.impl.local.model.BackgroundColorEntity
import com.titi.app.data.color.impl.local.model.ColorEntity

internal fun ColorRepositoryModel.toLocal() = ColorEntity(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor,
)

internal fun BackgroundColorRepositoryModel.toLocal() = BackgroundColorEntity(
    backgroundColors = backgroundColors,
)
