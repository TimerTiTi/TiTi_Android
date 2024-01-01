package com.titi.feature.main.ui.splash

import com.titi.doamin.daily.model.Daily
import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes

data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val daily: Daily? = null
)

fun SplashResultState.toFeatureTimeModel() = com.titi.feature.time.SplashResultState(
    recordTimes = recordTimes,
    timeColor = timeColor,
    daily = daily
)