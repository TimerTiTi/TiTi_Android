package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import javax.inject.Inject

class GetCurrentDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke() = dailyRepository.getCurrentDaily()?.toDomainModel()
}
