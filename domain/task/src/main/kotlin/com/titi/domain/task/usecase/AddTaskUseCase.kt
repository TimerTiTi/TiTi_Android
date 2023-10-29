package com.titi.domain.task.usecase

import com.titi.data.task.api.TaskRepository
import com.titi.domain.task.mapper.toRepositoryModel
import com.titi.domain.task.model.Task
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskName: String) {
        val task = taskRepository.getTaskByTaskName(taskName)
        val maxPosition = taskRepository.getMaxPosition()
        if (task == null) {
            taskRepository.upsertTask(
                Task(
                    id = 0,
                    position = maxPosition + 1,
                    taskName = taskName
                ).toRepositoryModel()
            )
        } else {
            taskRepository.upsertTask(
                task.copy(
                    isDelete = false,
                    position = maxPosition + 1
                )
            )
        }
    }

}