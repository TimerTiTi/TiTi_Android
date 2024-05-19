package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject

class AddDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(daily: Daily) {
        dailyRepository.upsert(daily.toRepositoryModel())
    }
}
