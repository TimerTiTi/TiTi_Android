package com.titi.data.daily.api.model

data class DailyRepositoryModel(
    val id: Long,
    val status: String?,
    val day: String,
    val timeline: List<Int>,
    val maxTime: Int,
    val tasks: Map<String, Int>?,
    val taskHistories: Map<String, List<TaskHistoryRepositoryModel>>?
)

data class TaskHistoryRepositoryModel(
    val startDate: String,
    val endDate: String,
)