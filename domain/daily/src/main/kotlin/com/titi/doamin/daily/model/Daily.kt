package com.titi.doamin.daily.model

import org.threeten.bp.LocalDateTime

data class Daily(
    val id: Long = 0,
    val status: String? = null,
    val day: LocalDateTime = LocalDateTime.now(),
    val timeLine: List<Long> = LongArray(24) { 0 }.toList(),
    val maxTime: Long = 0,
    val tasks: Map<String, Long>? = null,
    val taskHistories: Map<String, List<TaskHistory>>? = null
)

data class TaskHistory(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
