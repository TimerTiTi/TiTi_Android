package com.titi.data.time.impl.mapper

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import com.titi.data.time.impl.local.model.RecordTimesEntity

internal fun RecordTimesEntity.toRepositoryModel() = RecordTimesRepositoryModel(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt,
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    recordTask = recordTask,
)