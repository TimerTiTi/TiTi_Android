package com.titi.domain.time.usecase

import com.titi.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toRepositoryModel
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateSetGoalTimeUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(
        recordTimes: RecordTimes,
        setGoalTime: Long
    ) {
        if (recordTimes.setGoalTime != setGoalTime) {
            recordTimesRepository.setRecordTimes(
                recordTimes
                    .toRepositoryModel()
                    .copy(
                        setGoalTime = setGoalTime,
                        savedSumTime = 0,
                        savedTimerTime = recordTimes.setTimerTime,
                        savedStopWatchTime = 0,
                        savedGoalTime = setGoalTime
                    )
            )
        }
    }

}