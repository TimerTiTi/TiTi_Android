package com.titi.app.feature.log.mapper

import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import com.titi.app.feature.log.model.GraphGoalTimeUiState

internal fun GraphGoalTimeRepositoryModel.toFeatureModel() = GraphGoalTimeUiState(
    monthGoalTime = monthGoalTime,
    weekGoalTime = weekGoalTime,
)
