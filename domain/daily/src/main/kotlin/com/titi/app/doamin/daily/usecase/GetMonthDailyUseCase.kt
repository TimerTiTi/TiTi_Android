package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

class GetMonthDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(currentDate: LocalDate): List<Daily> {
        return dailyRepository.getDailies(
            startDateTime = currentDate
                .withDayOfMonth(1)
                .atStartOfDay(ZoneOffset.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toString(),
            endDateTime = currentDate
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toString(),
        )?.map { it.toDomainModel() }
            ?: emptyList()
    }
}
