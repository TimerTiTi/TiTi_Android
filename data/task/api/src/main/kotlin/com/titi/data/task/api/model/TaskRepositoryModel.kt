package com.titi.data.task.api.model

data class TaskRepositoryModel(
    val position: Long,
    val taskName: String,
    val taskTargetTime: Long,
    val isTaskTargetTimeOn: Boolean,
    val savedSumTime: Long,
    val isDelete: Boolean,
)
