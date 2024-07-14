package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllDailiesFlowUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    operator fun invoke(): Flow<List<Daily>?> = dailyRepository.getAllDailiesFlow()
        .map { dailies ->
            dailies?.map { it.toDomainModel() }
        }
}
