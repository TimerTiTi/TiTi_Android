package com.titi.data.time.mapper

import com.titi.data.time.local.model.RecordTimes

internal fun RecordTimes.toRepositoryModel() = com.titi.data.time.repository.model.RecordTimes(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt,
    setGoalTime = setGoalTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask,
)