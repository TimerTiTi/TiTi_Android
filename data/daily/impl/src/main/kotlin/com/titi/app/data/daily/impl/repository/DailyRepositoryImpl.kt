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
    override suspend fun getDateDaily(
        startDateTime: String,
        endDateTime: String,
    ): DailyRepositoryModel? {
        return dailyDao.getDateDaily(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        )?.toRepositoryModel()
    }

    override fun getDateDailyFlow(
        startDateTime: String,
        endDateTime: String,
    ): Flow<DailyRepositoryModel?> {
        return dailyDao.getDateDailyFlow(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        ).map { it?.toRepositoryModel() }
    }

    override suspend fun getDailies(
        startDateTime: String,
        endDateTime: String,
    ): List<DailyRepositoryModel>? {
        return dailyDao.getDailies(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        )?.map { it.toRepositoryModel() }
    }

    override suspend fun getAllDailies(): List<DailyRepositoryModel>? {
        return dailyDao.getAllDailies()?.map { it.toRepositoryModel() }
    }

    override suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel) {
        dailyDao.upsert(dailyRepositoryModel.toLocalModel())
    }
}
