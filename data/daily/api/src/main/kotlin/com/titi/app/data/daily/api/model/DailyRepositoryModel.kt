package com.titi.app.data.daily.api.model

data class DailyRepositoryModel(
    val id: Long,
    val status: String?,
    val day: String,
    val timeline: List<Long>,
    val maxTime: Long,
    val tasks: Map<String, Long>?,
    val taskHistories: Map<String, List<TaskHistoryRepositoryModel>>?
)

data class TaskHistoryRepositoryModel(
    val startDate: String,
    val endDate: String
)
