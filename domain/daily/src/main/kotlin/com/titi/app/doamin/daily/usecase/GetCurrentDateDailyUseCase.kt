package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

class GetCurrentDateDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {

    suspend operator fun invoke(currentDate: LocalDate): Result<Daily?> {
        val startDateTime = currentDate
            .atStartOfDay(ZoneOffset.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()
        val endDateTime = currentDate
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC).toString()

        return runCatching {
            dailyRepository.getDateDaily(
                startDateTime = startDateTime,
                endDateTime = endDateTime,
            )?.toDomainModel()
        }
    }
}
