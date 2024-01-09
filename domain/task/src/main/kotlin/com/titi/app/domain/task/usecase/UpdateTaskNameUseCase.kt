package com.titi.app.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import com.titi.app.domain.task.mapper.toRepositoryModel
import com.titi.app.domain.task.model.Task
import javax.inject.Inject

class UpdateTaskNameUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task, taskName: String) {
        if (!taskRepository.isExistTaskByTaskName(taskName)) {
            taskRepository.upsertTask(
                task.toRepositoryModel()
                    .copy(taskName = taskName)
            )
        }
    }

}