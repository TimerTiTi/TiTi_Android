package com.titi.domain.color.mapper

import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.domain.color.model.TimeColor

internal fun ColorRepositoryModel.toDomain() = TimeColor(
    timerBackgroundColor = timerBackgroundColor,
    timerTextColor = timerTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    stopwatchTextColor = stopwatchTextColor
)