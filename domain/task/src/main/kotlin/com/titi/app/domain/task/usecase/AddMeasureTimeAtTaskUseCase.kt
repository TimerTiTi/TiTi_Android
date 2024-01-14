package com.titi.app.domain.task.usecase

import com.titi.app.data.task.api.TaskRepository
import javax.inject.Inject

class AddMeasureTimeAtTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(taskName: String, measureTime: Long) {
        val task = taskRepository.getTaskByTaskName(taskName)
        task?.let { safeTask ->
            taskRepository.upsertTask(
                safeTask.copy(
                    savedSumTime = safeTask.savedSumTime + measureTime,
                ),
            )
        }
    }
}
