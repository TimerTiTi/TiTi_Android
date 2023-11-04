package com.titi.data.color.impl.mapper

import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.data.color.impl.local.model.ColorEntity

internal fun ColorRepositoryModel.toLocal() = ColorEntity(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor
)