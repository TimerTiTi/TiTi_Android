package com.titi.app.data.daily.impl.repository

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.impl.local.dao.DailyDao
import com.titi.app.data.daily.impl.mapper.toLocalModel
import com.titi.app.data.daily.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DailyRepositoryImpl @Inject constructor(
    private val dailyDao: DailyDao,
) : DailyRepository {
    override suspend fun getCurrentDaily(): DailyRepositoryModel? {
        return dailyDao.getCurrentDaily()?.toRepositoryModel()
    }

    override fun getCurrentDailyFlow(): Flow<DailyRepositoryModel?> {
        return dailyDao.getCurrentDailyFlow().map { it?.toRepositoryModel() }
    }

    override suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel) {
        dailyDao.upsert(dailyRepositoryModel.toLocalModel())
    }
}
