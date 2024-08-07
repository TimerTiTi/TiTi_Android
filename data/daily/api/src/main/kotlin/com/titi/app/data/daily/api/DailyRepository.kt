package com.titi.app.data.daily.api

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import kotlinx.coroutines.flow.Flow

interface DailyRepository {
    suspend fun getDateDaily(
        startDateTime: String = LocalDate
            .now()
            .minusDays(1)
            .atStartOfDay(ZoneOffset.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString(),
        endDateTime: String = LocalDate
            .now()
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString(),
    ): DailyRepositoryModel?

    fun getDateDailyFlow(
        startDateTime: String = LocalDate
            .now()
            .minusDays(1)
            .atStartOfDay(ZoneOffset.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString(),
        endDateTime: String = LocalDate
            .now()
            .atTime(23, 59, 59)
            .atZone(ZoneId.systemDefault())
            .withZoneSameInstant(ZoneOffset.UTC)
            .toString(),
    ): Flow<DailyRepositoryModel?>

    suspend fun getDailies(startDateTime: String, endDateTime: String): List<DailyRepositoryModel>?

    fun getDailiesFlow(
        startDateTime: String,
        endDateTime: String,
    ): Flow<List<DailyRepositoryModel>?>

    fun getAllDailiesFlow(): Flow<List<DailyRepositoryModel>?>

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)

    suspend fun setResetDailyEvent(daily: String)

    suspend fun getResetDailyEvent(): String?
}
