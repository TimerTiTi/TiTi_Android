package com.titi.app.feature.log.mapper

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.feature.log.model.CheckedButtonState

internal fun CheckedButtonState.toRepositoryModel() = GraphCheckedRepositoryModel(
    firstChecked = firstChecked,
    secondChecked = secondChecked,
    thirdChecked = thirdChecked,
    fourthChecked = fourthChecked,
)
