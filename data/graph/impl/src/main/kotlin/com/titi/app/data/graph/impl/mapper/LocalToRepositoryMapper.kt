package com.titi.app.data.graph.impl.mapper

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import com.titi.app.data.graph.impl.local.model.GraphCheckedEntity
import com.titi.app.data.graph.impl.local.model.GraphGoalTimeEntity

internal fun GraphCheckedEntity.toRepositoryModel() = GraphCheckedRepositoryModel(
    checkedButtonStates = checkedButtonStates,
)

internal fun GraphGoalTimeEntity.toRepositoryModel() = GraphGoalTimeRepositoryModel(
    monthGoalTime = monthGoalTime,
    weekGoalTime = weekGoalTime,
)
