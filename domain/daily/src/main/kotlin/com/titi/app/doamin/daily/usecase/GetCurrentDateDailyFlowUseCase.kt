package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCurrentDateDailyFlowUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    operator fun invoke(currentDate: LocalDate): Flow<Daily?> {
        val startDateTime = currentDate
            .atStartOfDay(ZoneOffset.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString()
        val endDateTime = currentDate
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC).toString()

        return dailyRepository.getDateDailyFlow(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        ).map { it?.toDomainModel() }
    }
}
