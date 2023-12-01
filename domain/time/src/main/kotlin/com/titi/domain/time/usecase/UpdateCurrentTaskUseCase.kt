package com.titi.domain.time.usecase

import com.titi.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toDomainModel
import com.titi.domain.time.mapper.toRepositoryModel
import com.titi.domain.time.model.CurrentTask
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateCurrentTaskUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(currentTask: CurrentTask) {
        val recordTimes = recordTimesRepository.getRecordTimes()?.toDomainModel() ?: RecordTimes()

        recordTimesRepository.setRecordTimes(
            recordTimes.copy(currentTask = currentTask).toRepositoryModel()
        )
    }

}