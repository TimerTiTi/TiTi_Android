package com.titi.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import com.titi.domain.task.mapper.toDomainModel
import com.titi.domain.task.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<List<Task>> =
        taskRepository.getTasks().map { taskRepositoryModels ->
            taskRepositoryModels.map {
                it.toDomainModel()
            }
        }

}