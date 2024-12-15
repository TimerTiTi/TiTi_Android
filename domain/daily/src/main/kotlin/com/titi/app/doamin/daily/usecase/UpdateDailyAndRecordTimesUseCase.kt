package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.data.time.api.model.RecordTimesRepositoryModel
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class UpdateDailyAndRecordTimesUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
    private val recordTimesRepository: RecordTimesRepository,
) {
    suspend operator fun invoke(daily: Daily) {
        val currentRecordTimes = recordTimesRepository.getRecordTimes()
        val currentTaskName = currentRecordTimes?.currentTaskRepositoryModel?.taskName

        var dailyTotalTime = 0L
        daily.taskHistories?.forEach { (_, taskHistories) ->
            taskHistories.forEach { taskHistory ->
                val startZoneDateTime = ZonedDateTime.parse(taskHistory.startDate)
                val endZoneDateTime = ZonedDateTime.parse(taskHistory.endDate)

                dailyTotalTime += Duration.between(startZoneDateTime, endZoneDateTime).seconds
            }
        }

        val dailyStartAt = daily.taskHistories?.values
            ?.flatten()
            ?.maxByOrNull { it.endDate }
            ?.startDate

        val updateRecordTimes = currentRecordTimes?.copy(
            recordStartAt = dailyStartAt ?: currentRecordTimes.recordStartAt,
            savedSumTime = dailyTotalTime,
            savedTimerTime = currentRecordTimes.setTimerTime,
            savedStopWatchTime = daily.tasks?.get(currentTaskName)
                ?: currentRecordTimes.savedStopWatchTime,
            savedGoalTime = currentRecordTimes.setGoalTime - dailyTotalTime,
        ) ?: RecordTimesRepositoryModel(
            recordingMode = 1,
            recording = false,
            recordStartAt = dailyStartAt,
            setGoalTime = 21600,
            setTimerTime = 3600,
            savedSumTime = 0,
            savedTimerTime = 3600,
            savedStopWatchTime = 0,
            savedGoalTime = 21600,
            currentTaskRepositoryModel = null,
        )

        recordTimesRepository.setRecordTimes(updateRecordTimes)
        dailyRepository.upsert(daily.toRepositoryModel())
    }
}
