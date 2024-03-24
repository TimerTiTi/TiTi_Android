package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.addTimeLine
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.TaskHistory
import javax.inject.Inject
import kotlin.math.max
import org.threeten.bp.ZonedDateTime

class AddMeasureTimeAtDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(
        taskName: String,
        startTime: String,
        endTime: String,
        measureTime: Long,
    ) {
        val recentDaily = dailyRepository.getDateDaily()?.toDomainModel()

        recentDaily?.let { daily ->
            val taskHistory = TaskHistory(
                startDate = startTime,
                endDate = endTime,
            )

            val updateTimeLine = addTimeLine(
                startTime = ZonedDateTime.parse(startTime),
                endTime = ZonedDateTime.parse(endTime),
                timeLine = daily.timeLine,
            )

            val updateMaxTime = max(daily.maxTime, measureTime)

            val updateTasks = daily.tasks?.toMutableMap()?.apply {
                this[taskName] = this[taskName]?.plus(measureTime) ?: measureTime
            }?.toMap() ?: mapOf(taskName to measureTime)

            val updateTaskHistories = daily.taskHistories?.toMutableMap()?.apply {
                this[taskName] =
                    this[taskName]?.toMutableList()?.apply { add(taskHistory) } ?: listOf(
                        taskHistory,
                    )
            }?.toMap() ?: mapOf(taskName to listOf(taskHistory))

            dailyRepository.upsert(
                daily.copy(
                    timeLine = updateTimeLine,
                    maxTime = updateMaxTime,
                    tasks = updateTasks,
                    taskHistories = updateTaskHistories,
                ).toRepositoryModel(),
            )
        }
    }
}
