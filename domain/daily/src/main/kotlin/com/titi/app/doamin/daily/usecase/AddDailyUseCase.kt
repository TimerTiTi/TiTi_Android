package com.titi.app.doamin.daily.usecase

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.doamin.daily.mapper.toRepositoryModel
import com.titi.app.doamin.daily.model.Daily
import javax.inject.Inject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

class AddDailyUseCase @Inject constructor(
    private val dailyRepository: DailyRepository,
) {
    suspend operator fun invoke() {
        val recentDaily = dailyRepository.getDateDaily()

        val currentDateTime = LocalDateTime.now()
        val dailyDayOfMonth = recentDaily?.let {
            ZonedDateTime
                .parse(it.day)
                .withZoneSameInstant(ZoneId.systemDefault())
                .dayOfMonth
        } ?: currentDateTime.dayOfMonth

        if (
            recentDaily == null ||
            (currentDateTime.dayOfMonth != dailyDayOfMonth && currentDateTime.hour >= 6)
        ) {
            dailyRepository.upsert(Daily().toRepositoryModel())
        } else {
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
        }
    }
}
