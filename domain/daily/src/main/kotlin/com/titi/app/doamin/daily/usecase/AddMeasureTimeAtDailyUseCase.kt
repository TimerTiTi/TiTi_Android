package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import com.titi.app.doamin.daily.model.TaskHistory
import com.titi.app.doamin.daily.model.toUpdateDaily
import javax.inject.Inject

class AddMeasureTimeAtDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(taskName: String, startTime: String, endTime: String) {
        val timePair = getDailyDayWithHour(6)
        val recentDaily = dailyRepository.getDateDaily(
            startDateTime = timePair.first,
            endDateTime = timePair.second,
        )?.toDomainModel() ?: Daily()

        val taskHistory = TaskHistory(
            startDate = startTime,
            endDate = endTime,
        )

        val updateTaskHistories = recentDaily.taskHistories?.toMutableMap()?.apply {
            this[taskName] = this[taskName]
                ?.toMutableList()
                ?.apply { add(taskHistory) }
                ?: listOf(taskHistory)
        }?.toMap()
            ?: mapOf(taskName to listOf(taskHistory))

        dailyRepository.upsert(
            recentDaily.toUpdateDaily(updateTaskHistories).toRepositoryModel(),
        )
    }
}
