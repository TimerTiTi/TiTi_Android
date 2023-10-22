package com.titi.domain.task.model

data class Task(
    val position: Long,
    val taskName: String,
    val taskTargetTime: Long = 3600,
    val isTaskTargetTimeOn: Boolean = false,
    val savedSumTime: Long,
    val isDelete: Boolean = false,
)
