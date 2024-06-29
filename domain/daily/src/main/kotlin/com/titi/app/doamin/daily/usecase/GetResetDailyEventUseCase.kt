package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import javax.inject.Inject

class GetResetDailyEventUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(): Boolean {
        val resetEventDate = dailyRepository.getResetDailyEvent()
        val (todayDate, endOfToday) = getDailyDayWithHour(6)

        return if (resetEventDate != null) {
            if (resetEventDate != todayDate) {
                dailyRepository.setResetDailyEvent(todayDate)
                true
            } else {
                false
            }
        } else {
            dailyRepository.setResetDailyEvent(todayDate)
            val dailyEvent = dailyRepository.getDateDaily(
                startDateTime = todayDate,
                endDateTime = endOfToday,
            )
            dailyEvent == null
        }
    }
}
