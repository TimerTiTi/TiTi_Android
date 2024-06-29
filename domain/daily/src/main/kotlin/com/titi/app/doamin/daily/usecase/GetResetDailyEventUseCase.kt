package com.titi.app.doamin.daily.usecase

import com.titi.app.core.util.getDailyDayWithHour
import com.titi.app.data.daily.api.DailyRepository
import javax.inject.Inject

class GetResetDailyEventUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke(): Boolean {
        val resetDaily = dailyRepository.getResetDailyEvent()
        val today = getDailyDayWithHour(6).first

        return if (resetDaily != null) {
            if (resetDaily != today) {
                dailyRepository.setResetDailyEvent(today)
                true
            } else {
                false
            }
        } else {
            dailyRepository.setResetDailyEvent(today)
            true
        }
    }
}
