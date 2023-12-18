package com.titi

import android.os.Parcelable
import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashResultState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor()
) : Parcelable
