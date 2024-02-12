package com.titi.app.data.daily.api

import com.titi.app.data.daily.api.model.DailyRepositoryModel
import kotlinx.coroutines.flow.Flow

interface DailyRepository {
    suspend fun getCurrentDaily(): DailyRepositoryModel?

    suspend fun getCurrentDateDaily(currentDate: String): DailyRepositoryModel?

    fun getCurrentDailyFlow(): Flow<DailyRepositoryModel?>

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)
}
