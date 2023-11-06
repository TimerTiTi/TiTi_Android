package com.titi.data.daily.impl.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dailies")
internal data class DailyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val status: String?,
    val day: String,
    val timeline: List<Int>,
    val maxTime: Int,
    val tasks: Map<String, Int>,
    val taskHistories: Map<String, List<TaskHistoryEntity>>
)

internal data class TaskHistoryEntity(
    val startDate: String,
    val endDate: String,
)