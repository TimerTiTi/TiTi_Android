package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModelWithRemovingSpecialCharacters
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject

class GetTodayDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(): Daily {
        val timePair = getDailyDayWithHour(6)

        val daily = dailyRepository.getDateDaily(
            startDateTime = timePair.first,
            endDateTime = timePair.second,
        )?.toDomainModelWithRemovingSpecialCharacters() ?: Daily()

        dailyRepository.upsert(daily.toRepositoryModel())

        return daily
    }
}
