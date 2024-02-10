package com.titi.app.feature.log.mapper

import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.domain.color.model.GraphColor
import com.titi.app.feature.log.model.GraphColorUiState

internal fun GraphColor.toFeatureModel() = GraphColorUiState(
    selectedIndex = selectedIndex,
    direction = direction,
    graphColors = graphColors.map { TdsColor.valueOf(it.name) },
)
