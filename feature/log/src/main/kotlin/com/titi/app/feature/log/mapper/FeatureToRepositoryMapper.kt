package com.titi.app.feature.log.mapper

import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import com.titi.app.feature.log.model.HomeUiState

internal fun HomeUiState.GraphGoalTime.toRepositoryModel() = GraphGoalTimeRepositoryModel(
    monthGoalTime = monthGoalTime,
    weekGoalTime = weekGoalTime,
)
