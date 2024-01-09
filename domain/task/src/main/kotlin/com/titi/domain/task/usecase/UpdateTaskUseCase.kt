package com.titi.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import com.titi.domain.task.mapper.toRepositoryModel
import com.titi.domain.task.model.Task
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(task: Task) {
        taskRepository.upsertTask(task.toRepositoryModel())
    }

}