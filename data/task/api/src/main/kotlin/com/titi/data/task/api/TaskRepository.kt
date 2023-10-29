package com.titi.data.task.api

import com.titi.data.task.api.model.TaskRepositoryModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(taskRepositoryModel: TaskRepositoryModel)

    suspend fun getMaxPosition(): Int

    suspend fun getTaskByTaskName(taskName: String): TaskRepositoryModel?

    fun getTasks(): Flow<List<TaskRepositoryModel>>

    suspend fun isExistTaskByTaskName(taskName: String): Boolean

    suspend fun updateTasksPosition(
        fromTask : TaskRepositoryModel,
        toTask : TaskRepositoryModel
    )

}