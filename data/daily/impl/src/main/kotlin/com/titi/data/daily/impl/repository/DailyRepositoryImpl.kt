package com.titi.data.daily.impl.repository

import com.titi.data.daily.api.DailyRepository
import com.titi.data.daily.api.model.DailyRepositoryModel
import com.titi.data.daily.impl.local.dao.DailyDao
import com.titi.data.daily.impl.mapper.toRepository
import javax.inject.Inject

internal class DailyRepositoryImpl @Inject constructor(
    private val dailyDao: DailyDao
) : DailyRepository {

    override suspend fun getDaily(): DailyRepositoryModel {
        return dailyDao.getDaily().toRepository()
    }

}