package com.titi.feature.time.usecase

import com.titi.data.time.repository.api.RecordTimesRepository
import javax.inject.Inject

class GetRecordTimesFlowUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    operator fun invoke() = recordTimesRepository.getRecordTimesFlow()

}