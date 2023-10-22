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
        val count = taskRepository.count()
        if (task == null) {
            taskRepository.upsertTask(
                Task(
                    position = count,
                    taskName = taskName
                ).toRepositoryModel()
            )
        } else {
            taskRepository.upsertTask(task.copy(isDelete = false))
        }
    }

}