package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject

class GetTodayDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(): Daily {
        val timePair = getDailyDayWithHour(6)

        return dailyRepository.getDateDaily(
            startDateTime = timePair.first,
            endDateTime = timePair.second,
        )?.toDomainModel() ?: Daily()
    }
}
