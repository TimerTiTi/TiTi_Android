package com.titi.feature.main.ui.splash

import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes

data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor()
)

fun SplashResultState.toFeatureTimeModel() = com.titi.feature.time.SplashResultState(
    recordTimes = recordTimes,
    timeColor = timeColor
)