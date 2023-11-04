package com.titi.domain.color.mapper

import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.domain.color.model.TimeColor

internal fun TimeColor.toRepository() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    timerTextColor = timerTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    stopwatchTextColor = stopwatchTextColor
)