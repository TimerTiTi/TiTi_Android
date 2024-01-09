package com.titi.app.data.time.api.model

data class RecordTimesRepositoryModel(
    val recordingMode: Int,
    val recording: Boolean,
    val recordStartAt: String?,
    val setGoalTime: Long,
    val setTimerTime: Long,
    val savedSumTime: Long,
    val savedTimerTime: Long,
    val savedStopWatchTime: Long,
    val savedGoalTime: Long,
    val currentTaskRepositoryModel: CurrentTaskRepositoryModel?,
)