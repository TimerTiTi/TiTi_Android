package com.titi.domain.color.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeColor(
    val timerBackgroundColor: Long = 0xFFA5C0E5,
    val isTimerBlackTextColor: Boolean = true,
    val stopwatchBackgroundColor: Long = 0xFF8C8FD3,
    val isStopwatchBlackTextColor: Boolean = true,
) : Parcelable
