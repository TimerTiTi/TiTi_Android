package com.titi.app.domain.task.mapper

import com.titi.app.data.task.api.model.TaskRepositoryModel
import com.titi.app.domain.task.model.Task

internal fun Task.toRepositoryModel() = TaskRepositoryModel(
    id = id,
    position = position,
    taskName = taskName,
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn,
    savedSumTime = savedSumTime,
    isDelete = isDelete,
)
