package com.titi.data.daily.api

import com.titi.data.daily.api.model.DailyRepositoryModel

interface DailyRepository {

    suspend fun getCurrentDaily() : DailyRepositoryModel?

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)

}