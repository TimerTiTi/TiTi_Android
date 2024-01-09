package com.titi.app.data.daily.impl.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "dailies")
internal data class DailyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val status: String?,
    val day: String,
    val timeline: List<Long>,
    val maxTime: Long,
    val tasks: Map<String, Long>?,
    val taskHistories: Map<String, List<TaskHistoryEntity>>?
)

@JsonClass(generateAdapter = true)
internal data class TaskHistoryEntity(
    val startDate: String,
    val endDate: String,
)