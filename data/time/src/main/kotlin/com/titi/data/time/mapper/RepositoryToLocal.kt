package com.titi.data.time.mapper

import com.titi.data.time.repository.model.RecordTimes

internal fun RecordTimes.toLocalModel() = com.titi.data.time.local.model.RecordTimes(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt,
    setGoalTime = setGoalTime,
    setTimerTime= setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask,
)