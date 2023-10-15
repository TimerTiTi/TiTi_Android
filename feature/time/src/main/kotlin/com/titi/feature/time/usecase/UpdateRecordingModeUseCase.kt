package com.titi.feature.time.usecase

import com.titi.data.time.repository.api.RecordTimesRepository
import com.titi.data.time.repository.model.RecordTimes
import javax.inject.Inject

class UpdateRecordingModeUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    suspend operator fun invoke(recordingMode: Int) {
        val updateRecordTimes = recordTimesRepository.getRecordTimes()?.let {
            it.copy(recordingMode = recordingMode)
        } ?: RecordTimes(recordingMode = recordingMode)

        recordTimesRepository.setRecordTimes(updateRecordTimes)
    }

}