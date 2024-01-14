package com.titi.app.data.time.impl.mapper

import com.titi.app.data.time.api.model.CurrentTaskRepositoryModel
import com.titi.app.data.time.api.model.RecordTimesRepositoryModel
import com.titi.app.data.time.impl.local.model.CurrentTaskEntity
import com.titi.app.data.time.impl.local.model.RecordTimesEntity

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
    currentTaskRepositoryModel = currentTaskEntity?.toRepositoryModel()
)

internal fun CurrentTaskEntity.toRepositoryModel() = CurrentTaskRepositoryModel(
    taskName = taskName,
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn
)
