package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodayDailyFlowUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    operator fun invoke(): Flow<Daily> {
        val hour = 6
        val timePair = getDailyDayWithHour(hour)

        return dailyRepository.getDateDailyFlow(
            startDateTime = timePair.first,
            endDateTime = timePair.second,
        ).map { it?.toDomainModel() ?: Daily() }
    }
}
