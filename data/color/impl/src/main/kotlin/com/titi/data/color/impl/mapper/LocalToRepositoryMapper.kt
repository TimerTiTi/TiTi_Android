package com.titi.data.color.impl.mapper

import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.data.color.impl.local.model.ColorEntity

internal fun ColorEntity.toRepositoryModel() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    timerTextColor = timerTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    stopwatchTextColor = stopwatchTextColor
)