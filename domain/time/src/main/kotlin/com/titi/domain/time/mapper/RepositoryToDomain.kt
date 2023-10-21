package com.titi.domain.time.mapper

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import com.titi.domain.time.model.RecordTimes

internal fun RecordTimesRepositoryModel.toDomainModel() = RecordTimes(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt,
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask
)