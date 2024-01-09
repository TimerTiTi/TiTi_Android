package com.titi.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.domain.time.mapper.toDomainModel
import com.titi.domain.time.model.RecordTimes
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecordTimesFlowUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository
) {

    operator fun invoke() = recordTimesRepository
        .getRecordTimesFlow()
        .map { it?.toDomainModel() ?: RecordTimes() }

}