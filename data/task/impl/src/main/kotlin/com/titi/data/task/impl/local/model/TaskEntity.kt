package com.titi.data.task.impl.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    indices = [Index(value = ["taskName"], unique = true)]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val position: Long,
    val taskName: String,
    val taskTargetTime: Long,
    val isTaskTargetTimeOn: Boolean,
    val savedSumTime: Long,
    val isDelete: Boolean = false,
)
