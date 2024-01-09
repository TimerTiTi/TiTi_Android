package com.titi.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.doamin.daily.mapper.toRepositoryModel
import com.titi.doamin.daily.model.Daily
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class AddDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository
) {

    suspend operator fun invoke() {
        val recentDaily = dailyRepository.getCurrentDaily()

        if (recentDaily != null) {
            dailyRepository.upsert(
                recentDaily.copy(
                    status = null,
                    day = ZonedDateTime.now(ZoneOffset.UTC).toString(),
                    timeline = LongArray(24) { 0 }.toList(),
                    maxTime = 0,
                    tasks = null,
                    taskHistories = null,
                )
            )
        } else {
            dailyRepository.upsert(Daily().toRepositoryModel())
        }
    }

}