package com.titi.domain.time.usecase

import com.titi.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toDomainModel
import com.titi.domain.time.mapper.toRepositoryModel
import com.titi.domain.time.model.CurrentTask
import com.titi.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateRecordTaskUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(recordTask: String) {
        val recordTimes = recordTimesRepository.getRecordTimes()?.toDomainModel() ?: RecordTimes()

        val updateRecordTimes = if (recordTimes.currentTask == null) {
            recordTimes.copy(currentTask = CurrentTask(taskName = recordTask))
        } else {
            recordTimes.copy(currentTask = recordTimes.currentTask.copy(taskName = recordTask))
        }

        recordTimesRepository.setRecordTimes(
            updateRecordTimes.toRepositoryModel()
        )
    }

}