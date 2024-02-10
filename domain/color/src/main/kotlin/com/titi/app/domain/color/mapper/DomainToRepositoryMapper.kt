package com.titi.app.domain.color.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.api.model.GraphColorRepositoryModel
import com.titi.app.domain.color.model.BackgroundColors
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.domain.color.model.TimeColor

internal fun TimeColor.toRepositoryModel() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor,
)

internal fun BackgroundColors.toRepositoryModel() = BackgroundColorRepositoryModel(
    backgroundColors = colors,
)

internal fun GraphColor.toRepositoryModel() = GraphColorRepositoryModel(
    selectedIndex = selectedIndex,
    direction = direction.name,
    graphColors = graphColors.map { it.name },
)
