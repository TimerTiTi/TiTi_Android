package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getMondaySunday
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import javax.inject.Inject

class GetWeekDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {

    suspend operator fun invoke(currentDate: LocalDate): Result<List<Daily>?> {
        val pairMonDaySunDay = getMondaySunday(currentDate)
        val startDateTime = pairMonDaySunDay
            .first
            .atStartOfDay(ZoneOffset.UTC)
            .toString()
            .substring(0, 10) + "T00:00:00Z"
        val endDateTime = pairMonDaySunDay
            .second
            .atStartOfDay(ZoneOffset.UTC)
            .toString()
            .substring(0, 10) + "T23:59:59:Z"

        return runCatching {
            dailyRepository.getWeekDaily(
                startDateTime = startDateTime,
                endDateTime = endDateTime,
            )?.map { it.toDomainModel() }
        }
    }

}