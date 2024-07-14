package com.titi.app.feature.log.mapper

import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import com.titi.app.feature.log.model.HomeUiState

internal fun GraphGoalTimeRepositoryModel.toFeatureModel() = HomeUiState.GraphGoalTime(
    monthGoalTime = monthGoalTime,
    weekGoalTime = weekGoalTime,
)
