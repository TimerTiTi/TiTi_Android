package com.titi.app.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.domain.time.mapper.toDomainModel
import com.titi.app.domain.time.mapper.toRepositoryModel
import com.titi.app.domain.time.model.RecordTimes
import javax.inject.Inject

class UpdateRecordingModeUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {
    suspend operator fun invoke(recordingMode: Int) {
        val recordTimes = recordTimesRepository.getRecordTimes()?.toDomainModel() ?: RecordTimes()
        if (recordTimes.recordingMode != recordingMode) {
            recordTimesRepository.setRecordTimes(
                recordTimes
                    .toRepositoryModel()
                    .copy(recordingMode = recordingMode)
            )
        }
    }
}
