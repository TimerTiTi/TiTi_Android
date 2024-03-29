package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

class HasDailyForCurrentMonthUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(currentDate: LocalDate): List<LocalDate> {
        val startDay = 1
        val lastDay = currentDate.lengthOfMonth()
        val hasDaily = mutableListOf<LocalDate>()

        val dailies = dailyRepository.getDailies(
            startDateTime = currentDate
                .withDayOfMonth(startDay)
                .atStartOfDay(ZoneOffset.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toString(),
            endDateTime = currentDate
                .withDayOfMonth(lastDay)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneOffset.UTC)
                .toString(),
        )

        dailies?.forEach {
            val zonedDateTime =
                ZonedDateTime.parse(it.day).withZoneSameInstant(ZoneId.systemDefault())
            hasDaily.add(zonedDateTime.toLocalDate())
        }

        return hasDaily.toList()
    }
}
