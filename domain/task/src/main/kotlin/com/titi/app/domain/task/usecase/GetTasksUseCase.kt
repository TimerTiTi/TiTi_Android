package com.titi.app.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import com.titi.app.domain.task.mapper.toDomainModel
import com.titi.app.domain.task.model.Task
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    operator fun invoke(): Flow<List<Task>> =
        taskRepository.getTasks().map { taskRepositoryModels ->
            taskRepositoryModels.map {
                it.toDomainModel()
            }
        }
}
