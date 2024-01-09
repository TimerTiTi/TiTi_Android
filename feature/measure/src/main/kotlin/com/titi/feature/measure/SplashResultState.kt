package com.titi.feature.measure

import android.os.Parcelable
import com.titi.doamin.daily.model.Daily
import com.titi.app.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val daily : Daily? = null
) : Parcelable
