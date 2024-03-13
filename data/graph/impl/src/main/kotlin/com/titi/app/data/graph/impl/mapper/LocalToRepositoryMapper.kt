package com.titi.app.data.graph.impl.mapper

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.data.graph.impl.local.model.GraphCheckedEntity

internal fun GraphCheckedEntity.toRepositoryModel() = GraphCheckedRepositoryModel(
    checkedButtonStates = checkedButtonStates,
)
