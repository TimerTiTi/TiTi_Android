package com.titi.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toRepositoryModel
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class AddMeasureTimeAtRecordTimesUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(
        recordTimes: RecordTimes,
        measureTime: Long
    ) {
        val updateRecordTimes = if (recordTimes.recordingMode == 1) {
            recordTimes.copy(
                recording = false,
                savedSumTime = recordTimes.savedSumTime + measureTime,
                savedTimerTime = recordTimes.savedTimerTime - measureTime,
                savedGoalTime = recordTimes.savedGoalTime - measureTime
            )
        } else {
            recordTimes.copy(
                recording = false,
                savedSumTime = recordTimes.savedSumTime + measureTime,
                savedStopWatchTime = recordTimes.savedStopWatchTime + measureTime,
                savedGoalTime = recordTimes.savedGoalTime - measureTime
            )
        }

        recordTimesRepository.setRecordTimes(updateRecordTimes.toRepositoryModel())
    }

}