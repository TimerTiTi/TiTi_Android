package com.titi.app.doamin.daily.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

@Parcelize
data class Daily(
    val id: Long = 0,
    val status: String? = null,
    val day: String = ZonedDateTime.now(ZoneOffset.UTC).toString(),
    val timeLine: List<Long> = LongArray(24) { 0 }.toList(),
    val maxTime: Long = 0,
    val tasks: Map<String, Long>? = null,
    val taskHistories: Map<String, List<TaskHistory>>? = null
) : Parcelable

@Parcelize
data class TaskHistory(
    val startDate: String,
    val endDate: String,
) : Parcelable
