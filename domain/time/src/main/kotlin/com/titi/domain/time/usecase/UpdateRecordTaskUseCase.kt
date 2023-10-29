package com.titi.domain.time.usecase

import com.titi.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toDomainModel
import com.titi.domain.time.mapper.toRepositoryModel
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateRecordTaskUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(recordTask: String) {
        val recordTimes = recordTimesRepository.getRecordTimes()?.toDomainModel() ?: RecordTimes()
        if (recordTimes.recordTask != recordTask) {
            recordTimesRepository.setRecordTimes(
                recordTimes
                    .toRepositoryModel()
                    .copy(recordTask = recordTask)
            )
        }
    }

}