package com.titi.app.domain.task.mapper

import com.titi.app.core.util.removeSpecialCharacter
import com.titi.app.data.task.api.model.TaskRepositoryModel
import com.titi.app.domain.task.model.Task

internal fun TaskRepositoryModel.toDomainModel() = Task(
    id = id,
    position = position,
    taskName = taskName.removeSpecialCharacter(),
    taskTargetTime = taskTargetTime,
    isTaskTargetTimeOn = isTaskTargetTimeOn,
    savedSumTime = savedSumTime,
    isDelete = isDelete,
)
