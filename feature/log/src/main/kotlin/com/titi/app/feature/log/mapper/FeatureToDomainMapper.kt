package com.titi.app.feature.log.mapper

import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.GraphColorUiState

internal fun GraphColorUiState.toDomainModel() = GraphColor(
    selectedIndex = selectedIndex,
    direction = direction,
    graphColors = graphColors.map { GraphColor.GraphColorType.valueOf(it.name) },
)
