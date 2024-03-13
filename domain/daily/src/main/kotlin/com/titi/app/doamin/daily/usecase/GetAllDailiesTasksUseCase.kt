package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import javax.inject.Inject

class GetAllDailiesTasksUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(): Map<String, Long> {
        val taskMap = mutableMapOf<String, Long>()

        dailyRepository.getAllDailies()?.let { dailies ->
            dailies.forEach { daily ->
                daily.tasks?.let { task ->
                    task.forEach { (taskName, taskTime) ->
                        taskMap[taskName] = taskMap.getOrDefault(taskName, 0L) + taskTime
                    }
                }
            }
        }

        return taskMap.toMap()
    }
}
