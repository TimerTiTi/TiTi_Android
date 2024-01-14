package com.titi.app.domain.time.usecase

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.domain.time.mapper.toDomainModel
import com.titi.app.domain.time.model.RecordTimes
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class GetRecordTimesFlowUseCase @Inject constructor(
    private val recordTimesRepository: RecordTimesRepository,
) {
    operator fun invoke() = recordTimesRepository
        .getRecordTimesFlow()
        .map { it?.toDomainModel() ?: RecordTimes() }
}
