package com.titi.app.domain.time.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordTimes(
    val recordingMode: Int = 1,
    val recording: Boolean = false,
    val recordStartAt: String? = null,
    val setGoalTime: Long = 21600,
    val setTimerTime: Long = 3600,
    val savedSumTime: Long = 0,
    val savedTimerTime: Long = 3600,
    val savedStopWatchTime: Long = 0,
    val savedGoalTime: Long = 21600,
    val currentTask: CurrentTask? = null,
) : Parcelable
