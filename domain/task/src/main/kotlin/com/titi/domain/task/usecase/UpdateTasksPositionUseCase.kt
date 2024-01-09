package com.titi.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import com.titi.domain.task.mapper.toRepositoryModel
import com.titi.domain.task.model.Task
import javax.inject.Inject

class UpdateTasksPositionUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(fromTask: Task, toTask: Task) {
        taskRepository.updateTasksPosition(
            fromTask = fromTask.toRepositoryModel().copy(position = toTask.position),
            toTask = toTask.toRepositoryModel().copy(position = fromTask.position)
        )
    }

}