package com.titi.data.daily.impl.repository

import com.titi.data.daily.api.DailyRepository
import com.titi.data.daily.api.model.DailyRepositoryModel
import com.titi.data.daily.impl.local.dao.DailyDao
import com.titi.data.daily.impl.mapper.toLocal
import com.titi.data.daily.impl.mapper.toRepository
import javax.inject.Inject

internal class DailyRepositoryImpl @Inject constructor(
    private val dailyDao: DailyDao
) : DailyRepository {

    override suspend fun getCurrentDaily(): DailyRepositoryModel? {
        return dailyDao.getCurrentDaily()?.toRepository()
    }

    override suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel) {
        dailyDao.upsert(dailyRepositoryModel.toLocal())
    }

}