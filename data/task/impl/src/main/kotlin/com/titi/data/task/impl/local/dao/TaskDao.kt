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

    @Query("SELECT MAX(position) FROM tasks")
    suspend fun getMaxPosition(): Int

    @Query("SELECT * FROM tasks WHERE taskName = :taskName")
    suspend fun getTaskByTaskName(taskName: String): TaskEntity?

    @Query("SELECT * FROM tasks WHERE NOT isDelete ORDER BY position DESC")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT EXISTS(SELECT * FROM tasks WHERE taskName = :taskName)")
    suspend fun isExistTaskByTaskName(taskName : String) : Boolean

}