package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getMondaySunday
import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toDomainModel
import com.titi.app.doamin.daily.model.Daily
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCurrentWeekDailiesFlowUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    operator fun invoke(currentDate: LocalDate): Flow<List<Daily>?> {
        val (monday, sunday) = getMondaySunday(currentDate)

        return dailyRepository.getDailiesFlow(
            startDateTime = monday.toString(),
            endDateTime = sunday.toString(),
        ).map { dailies ->
            dailies?.map { it.toDomainModel() }
        }
    }
}
