package com.titi.data.time.api.model

data class CurrentTaskRepositoryModel(
    val taskName: String,
    val taskTargetTime: Long,
    val isTaskTargetTimeOn: Boolean,
)
