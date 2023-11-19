package com.titi.domain.task.usecase

import com.titi.data.task.api.TaskRepository
import javax.inject.Inject

class AddTaskSumTimeUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(
        taskName: String,
        measureTime: Long,
    ) {
        val task = taskRepository.getTaskByTaskName(taskName)
        task?.let { safeTask ->
            taskRepository.upsertTask(
                safeTask.copy(
                    savedSumTime = safeTask.savedSumTime + measureTime
                )
            )
        }
    }

}