package com.titi.app.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.domain.time.mapper.toRepositoryModel
import com.titi.app.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateSetGoalTimeUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository,
) {
    suspend operator fun invoke(recordTimes: RecordTimes, setGoalTime: Long) {
        recordTimesRepository.setRecordTimes(
            recordTimes
                .toRepositoryModel()
                .copy(
                    setGoalTime = setGoalTime,
                    savedGoalTime = setGoalTime -
                        (recordTimes.setGoalTime - recordTimes.savedGoalTime),
                ),
        )
    }
}
