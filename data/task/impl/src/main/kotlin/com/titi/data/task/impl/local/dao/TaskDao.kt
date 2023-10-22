package com.titi.data.task.impl.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.titi.data.task.impl.local.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TaskDao {

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Query("SELECT count(*) FROM tasks")
    suspend fun count(): Int

    @Query("SELECT * FROM tasks WHERE taskName = :taskName")
    suspend fun getTaskByTaskName(taskName: String): TaskEntity?

    @Query("SELECT * FROM tasks ORDER BY position ASC")
    fun getTasks(): Flow<TaskEntity>

}