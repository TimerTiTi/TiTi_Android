package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class GetCurrentDateDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {

    suspend operator fun invoke(currentDate: LocalDate): Result<Daily?> {
        val currentDateString = currentDate.atStartOfDay(ZoneOffset.UTC).toString()
        return runCatching {
            dailyRepository.getCurrentDateDaily(currentDateString)?.toDomainModel()
        }
    }
}
