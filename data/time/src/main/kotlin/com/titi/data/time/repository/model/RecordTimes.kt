package com.titi.data.time.repository.model

data class RecordTimes(
    val recordingMode: Int,
    val recording: Boolean,
    val recordStartAt: String,
    val setGoalTime: Long,
    val savedSumTime: Long,
    val savedTimerTime: Long,
    val savedGoalTime: Long,
    val recordTask: String,
    val recordTimeLine: List<Long>,
)