package com.titi.doamin.daily.usecase

import com.titi.core.util.addTimeLine
import com.titi.data.daily.api.DailyRepository
import com.titi.doamin.daily.mapper.toDomain
import com.titi.doamin.daily.mapper.toRepositoryModel
import com.titi.doamin.daily.model.TaskHistory
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import kotlin.math.max

class AddMeasureTimeAtDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository
) {

    suspend operator fun invoke(
        taskName: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        measureTime: Long
    ) {
        val recentDaily = dailyRepository.getCurrentDaily()?.toDomain()

        recentDaily?.let { daily ->
            val taskHistory = TaskHistory(
                startDate = startTime,
                endDate = endTime
            )

            val updateTimeLine = addTimeLine(
                startTime = startTime,
                endTime = endTime,
                timeLine = daily.timeLine
            )

            val updateMaxTime = max(daily.maxTime, measureTime)

            val updateTasks = daily.tasks?.toMutableMap()?.apply {
                this[taskName] = this[taskName]?.plus(measureTime) ?: measureTime
            }?.toMap() ?: mapOf(taskName to measureTime)

            val updateTaskHistories = daily.taskHistories?.toMutableMap()?.apply {
                this[taskName] =
                    this[taskName]?.toMutableList()?.apply { add(taskHistory) } ?: listOf(
                        taskHistory
                    )
            }?.toMap() ?: mapOf(taskName to listOf(taskHistory))

            dailyRepository.upsert(
                daily.copy(
                    timeLine = updateTimeLine,
                    maxTime = updateMaxTime,
                    tasks = updateTasks,
                    taskHistories = updateTaskHistories
                ).toRepositoryModel()
            )
        }
    }

}