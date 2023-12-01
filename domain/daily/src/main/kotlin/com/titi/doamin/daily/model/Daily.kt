package com.titi.doamin.daily.model

import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

data class Daily(
    val id: Long = 0,
    val status: String? = null,
    val day: ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC),
    val timeLine: List<Long> = LongArray(24) { 0 }.toList(),
    val maxTime: Long = 0,
    val tasks: Map<String, Long>? = null,
    val taskHistories: Map<String, List<TaskHistory>>? = null
)

data class TaskHistory(
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
)
