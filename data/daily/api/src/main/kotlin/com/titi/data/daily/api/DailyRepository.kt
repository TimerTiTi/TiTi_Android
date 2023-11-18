package com.titi.data.daily.api

import com.titi.data.daily.api.model.DailyRepositoryModel
import kotlinx.coroutines.flow.Flow

interface DailyRepository {

    suspend fun getCurrentDaily() : DailyRepositoryModel?

    fun getCurrentDailyFlow() : Flow<DailyRepositoryModel?>

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)

}