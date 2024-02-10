package com.titi.app.data.color.impl.mapper

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.api.model.GraphColorRepositoryModel
import com.titi.app.data.color.impl.local.model.BackgroundColorEntity
import com.titi.app.data.color.impl.local.model.ColorEntity
import com.titi.app.data.color.impl.local.model.GraphColorEntity

internal fun ColorEntity.toRepositoryModel() = ColorRepositoryModel(
    timerBackgroundColor = timerBackgroundColor,
    isTimerBlackTextColor = isTimerBlackTextColor,
    stopwatchBackgroundColor = stopwatchBackgroundColor,
    isStopwatchBlackTextColor = isStopwatchBlackTextColor,
)

internal fun BackgroundColorEntity.toRepositoryModel() = BackgroundColorRepositoryModel(
    backgroundColors = backgroundColors,
)

internal fun GraphColorEntity.toRepositoryModel() = GraphColorRepositoryModel(
    selectedIndex = selectedIndex,
    direction = direction,
    graphColors = graphColors,
)
