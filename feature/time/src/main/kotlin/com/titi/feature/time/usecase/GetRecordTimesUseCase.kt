package com.titi.feature.time.usecase

import com.titi.data.time.repository.api.RecordTimesRepository
import javax.inject.Inject

class GetRecordTimesUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke() = recordTimesRepository.getRecordTimes()

}