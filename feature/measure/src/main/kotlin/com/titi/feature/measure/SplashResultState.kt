package com.titi.feature.measure

import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes

data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor()
)
