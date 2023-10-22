package com.titi.data.task.impl.mapper

import com.titi.data.task.api.model.TaskRepositoryModel
import com.titi.data.task.impl.local.model.TaskEntity

internal fun TaskRepositoryModel.toLocalModel() = TaskEntity(
    position = position,
    taskName = taskName,
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn,
    savedSumTime = savedSumTime,
    isDelete = isDelete
)