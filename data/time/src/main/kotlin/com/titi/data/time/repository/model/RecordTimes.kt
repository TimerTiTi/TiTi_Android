package com.titi.data.time.repository.model

data class RecordTimes(
    val recordingMode: Int = 1,
    val recording: Boolean = false,
    val recordStartAt: String = "",
    val setGoalTime: Long = 0,
    val savedSumTime: Long = 0,
    val savedTimerTime: Long = 0,
    val savedGoalTime: Long = 0,
    val recordTask: String = "",
    val recordTimeLine: List<Long> = emptyList(),
)