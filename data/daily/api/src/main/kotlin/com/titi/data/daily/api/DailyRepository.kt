package com.titi.data.daily.api

import com.titi.data.daily.api.model.DailyRepositoryModel

interface DailyRepository {

    suspend fun getDaily() : DailyRepositoryModel?

    suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel)

}