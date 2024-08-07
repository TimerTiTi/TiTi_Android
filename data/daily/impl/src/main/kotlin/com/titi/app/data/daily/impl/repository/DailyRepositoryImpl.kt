package com.titi.app.data.daily.impl.repository

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.data.daily.api.model.DailyRepositoryModel
import com.titi.app.data.daily.impl.local.dao.DailyDao
import com.titi.app.data.daily.impl.local.datastore.DailyDataStore
import com.titi.app.data.daily.impl.mapper.toLocalModel
import com.titi.app.data.daily.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DailyRepositoryImpl @Inject constructor(
    private val dailyDao: DailyDao,
    private val dailyDataStore: DailyDataStore,
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

    override fun getDailiesFlow(
        startDateTime: String,
        endDateTime: String,
    ): Flow<List<DailyRepositoryModel>?> {
        return dailyDao.getDailiesFlow(
            startDateTime = startDateTime,
            endDateTime = endDateTime,
        ).map { dailies ->
            dailies?.map { it.toRepositoryModel() }
        }
    }

    override fun getAllDailiesFlow(): Flow<List<DailyRepositoryModel>?> {
        return dailyDao.getAllDailiesFlow().map { dailies ->
            dailies?.map { it.toRepositoryModel() }
        }
    }

    override suspend fun upsert(dailyRepositoryModel: DailyRepositoryModel) {
        dailyDao.upsert(dailyRepositoryModel.toLocalModel())
    }

    override suspend fun setResetDailyEvent(daily: String) {
        dailyDataStore.setResetDailyEvent(daily)
    }

    override suspend fun getResetDailyEvent(): String? {
        return dailyDataStore.getResetDailyEvent()
    }
}
