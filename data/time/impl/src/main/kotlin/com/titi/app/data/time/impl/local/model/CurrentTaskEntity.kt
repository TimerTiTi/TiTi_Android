package com.titi.app.data.time.impl.local.model

data class CurrentTaskEntity(
    val taskName: String,
    val taskTargetTime: Long,
    val isTaskTargetTimeOn: Boolean
)
