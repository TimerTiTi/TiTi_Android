package com.titi.app.feature.main.ui

import com.titi.app.doamin.daily.model.Daily
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.time.model.RecordTimes

data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val daily: Daily? = null,
)

fun SplashResultState.toFeatureTimeModel() = com.titi.app.feature.time.SplashResultState(
    recordTimes = recordTimes,
    timeColor = timeColor,
    daily = daily,
)
