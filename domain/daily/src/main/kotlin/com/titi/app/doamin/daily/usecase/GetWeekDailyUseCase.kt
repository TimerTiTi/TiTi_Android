package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class GetWeekDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {

    suspend operator fun invoke(currentDate: LocalDate): Result<List<Daily>?> {
        val pairMonDaySunDay = getMondaySunday(currentDate)

        val startDateTime = pairMonDaySunDay.first
        val endDateTime = pairMonDaySunDay.second

        return runCatching {
            dailyRepository.getDailies(
                startDateTime = startDateTime,
                endDateTime = endDateTime,
            )?.map { it.toDomainModel() }
        }
    }
}

fun getMondaySunday(currentDate: LocalDate): Pair<String, String> {
    val diffMonday = currentDate.dayOfWeek.value - DayOfWeek.MONDAY.value
    val diffSunday = DayOfWeek.SUNDAY.value - currentDate.dayOfWeek.value

    val monday = currentDate
        .minusDays(diffMonday.toLong())
        .atStartOfDay()
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)
        .toString()

    val sunday = currentDate
        .plusDays(diffSunday.toLong())
        .atTime(23, 59, 59)
        .atZone(ZoneOffset.systemDefault())
        .withZoneSameInstant(ZoneOffset.UTC)
        .toString()

    return Pair(monday, sunday)
}
