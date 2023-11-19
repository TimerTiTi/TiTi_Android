package com.titi.domain.time.mapper

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import com.titi.domain.time.model.RecordTimes

internal fun RecordTimes.toRepositoryModel() = RecordTimesRepositoryModel(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt?.toString() ?:"",
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask
)