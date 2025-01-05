package com.titi.app.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.domain.time.mapper.toDomainModel
import com.titi.app.domain.time.mapper.toRepositoryModel
import com.titi.app.domain.time.model.RecordTimes
import javax.inject.Inject

class GetRecordTimesUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository,
) {
    suspend operator fun invoke(): RecordTimes {
        val recordTimes = recordTimesRepository.getRecordTimes()
            ?.toDomainModel()
            ?: RecordTimes()

        recordTimesRepository.setRecordTimes(recordTimes.toRepositoryModel())
        return recordTimes
    }
}
