package com.titi.domain.time.usecase

import com.titi.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toDomainModel
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class GetRecordTimesUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke() =
        recordTimesRepository.getRecordTimes()?.toDomainModel() ?: RecordTimes()

}