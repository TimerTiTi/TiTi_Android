package com.titi.app.feature.log.mapper

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.feature.log.model.CheckedButtonState

internal fun GraphCheckedRepositoryModel.toFeatureModel() = CheckedButtonState(
    firstChecked = firstChecked,
    secondChecked = secondChecked,
    thirdChecked = thirdChecked,
    fourthChecked = fourthChecked,
)
