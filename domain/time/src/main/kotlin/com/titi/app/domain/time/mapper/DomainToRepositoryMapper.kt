package com.titi.app.domain.time.mapper

import com.titi.app.data.time.api.model.CurrentTaskRepositoryModel
import com.titi.app.data.time.api.model.RecordTimesRepositoryModel
import com.titi.app.domain.time.model.CurrentTask
import com.titi.app.domain.time.model.RecordTimes

internal fun RecordTimes.toRepositoryModel() = RecordTimesRepositoryModel(
    recordingMode = recordingMode,
    recording = recording,
    recordStartAt = recordStartAt ?: "",
    setGoalTime = setGoalTime,
    setTimerTime = setTimerTime,
    savedSumTime = savedSumTime,
    savedTimerTime = savedTimerTime,
    savedStopWatchTime = savedStopWatchTime,
    savedGoalTime = savedGoalTime,
    currentTaskRepositoryModel = currentTask?.toRepositoryModel(),
)

internal fun CurrentTask.toRepositoryModel() = CurrentTaskRepositoryModel(
    taskName = taskName,
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn,
)
