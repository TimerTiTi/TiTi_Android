package com.titi.data.task.impl.repository

import com.titi.data.task.api.TaskRepository
import com.titi.data.task.api.model.TaskRepositoryModel
import com.titi.data.task.impl.local.dao.TaskDao
import com.titi.data.task.impl.mapper.toLocalModel
import com.titi.data.task.impl.mapper.toRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun upsertTask(taskRepositoryModel: TaskRepositoryModel) {
        taskDao.upsertTask(taskRepositoryModel.toLocalModel())
    }

    override suspend fun count(): Int =
        taskDao.count()

    override suspend fun getTaskByTaskName(taskName: String): TaskRepositoryModel? =
        taskDao.getTaskByTaskName(taskName = taskName)?.toRepositoryModel()

    override fun getTasks(): Flow<List<TaskRepositoryModel>> =
        taskDao.getTasks().map { taskEntities ->
            taskEntities.map {
                it.toRepositoryModel()
            }
        }

    override suspend fun isExistTaskByTaskName(taskName: String): Boolean =
        taskDao.isExistTaskByTaskName(taskName)

}