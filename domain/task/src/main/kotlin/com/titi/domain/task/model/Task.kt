package com.titi.domain.task.model

data class Task(
    val id : Long,
    val position: Int,
    val taskName: String,
    val taskTargetTime: Long = 3600,
    val isTaskTargetTimeOn: Boolean = false,
    val savedSumTime: Long = 0,
    val isDelete: Boolean = false,
)
