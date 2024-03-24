package com.titi.app.domain.color.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.api.model.GraphColorRepositoryModel
import com.titi.app.domain.color.model.BackgroundColors
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.domain.color.model.TimeColor

internal fun ColorRepositoryModel.toDomainModel() = TimeColor(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor,
)

internal fun BackgroundColorRepositoryModel.toDomainModel() = BackgroundColors(
    colors = backgroundColors,
)

internal fun GraphColorRepositoryModel.toDomainModel() = GraphColor(
    selectedIndex = selectedIndex,
    direction = GraphColor.GraphDirection.valueOf(direction),
    graphColors = graphColors.map { GraphColor.GraphColorType.valueOf(it) },
)
