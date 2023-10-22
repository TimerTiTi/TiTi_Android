package com.titi.domain.task.mapper

import com.titi.data.task.api.model.TaskRepositoryModel
import com.titi.domain.task.model.Task

internal fun TaskRepositoryModel.toDomainModel() = Task(
    id = id,
    position = position,
    taskName = taskName,
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn,
    savedSumTime = savedSumTime,
    isDelete = isDelete
)