package com.titi.app.data.task.impl.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    indices = [Index(value = ["taskName"], unique = true)]
)
internal data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val position: Int,
    val taskName: String,
    val taskTargetTime: Long,
    val isTaskTargetTimeOn: Boolean,
    val savedSumTime: Long,
    val isDelete: Boolean,
)
