package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class AddDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke() {
        val recentDaily = dailyRepository.getDateDaily()

        if (recentDaily != null) {
            dailyRepository.upsert(
                recentDaily.copy(
                    status = null,
                    day = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                    timeline = LongArray(24) { 0 }.toList(),
                    maxTime = 0,
                    tasks = null,
                    taskHistories = null,
                ),
            )
        } else {
            dailyRepository.upsert(Daily().toRepositoryModel())
        }
    }
}
