package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.TaskHistory
import com.titi.app.doamin.daily.model.toUpdateDaily
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

class AddMeasureTimeAtDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(taskName: String, startTime: String, endTime: String) {
        val recentDaily = dailyRepository.getDateDaily(
            startDateTime = LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toString(),
        )?.toDomainModel()

        recentDaily?.let { daily ->
            val taskHistory = TaskHistory(
                startDate = startTime,
                endDate = endTime,
            )

            val updateTaskHistories = daily.taskHistories?.toMutableMap()?.apply {
                this[taskName] = this[taskName]
                    ?.toMutableList()
                    ?.apply { add(taskHistory) }
                    ?: listOf(taskHistory)
            }?.toMap()
                ?: mapOf(taskName to listOf(taskHistory))

            dailyRepository.upsert(
                daily.toUpdateDaily(updateTaskHistories).toRepositoryModel(),
            )
        }
    }
}
