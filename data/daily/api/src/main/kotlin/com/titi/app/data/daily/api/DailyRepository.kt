package com.titi.app.data.daily.api

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.flow.Flow

interface DailyRepository {
    suspend fun getDateDaily(
        startDateTime: String = LocalDate
            .now()
            .minusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toString(),
        endDateTime: String = LocalDate
            .now()
            .atStartOfDay(ZoneOffset.UTC)
            .toString()
            .substring(0, 10) + "T23:59:59Z",
    ): DailyRepositoryModel?

    suspend fun getDailies(startDateTime: String, endDateTime: String): List<DailyRepositoryModel>?

    fun getDateDailyFlow(
        startDateTime: String = LocalDate
            .now()
            .minusDays(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toString(),
        endDateTime: String = LocalDate
            .now()
            .atStartOfDay(ZoneOffset.UTC)
            .toString()
            .substring(0, 10) + "T23:59:59Z",
    ): Flow<DailyRepositoryModel?>

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)
}
