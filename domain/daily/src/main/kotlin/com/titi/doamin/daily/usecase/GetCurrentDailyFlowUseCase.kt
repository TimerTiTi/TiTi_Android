package com.titi.doamin.daily.usecase

import com.titi.data.daily.api.DailyRepository
import com.titi.doamin.daily.mapper.toDomain
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentDailyFlowUseCase @Inject constructor(
    private val dailyRepository: DailyRepository
) {

    operator fun invoke() =
        dailyRepository.getCurrentDailyFlow().map {
            it?.toDomain()
        }

}