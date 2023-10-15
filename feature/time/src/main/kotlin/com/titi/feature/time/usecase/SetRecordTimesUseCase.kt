package com.titi.feature.time.usecase

import com.titi.data.time.repository.api.RecordTimesRepository
import com.titi.data.time.repository.model.RecordTimes
import javax.inject.Inject

class SetRecordTimesUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(recordTimes: RecordTimes) {
        recordTimesRepository.setRecordTimes(recordTimes)
    }

}