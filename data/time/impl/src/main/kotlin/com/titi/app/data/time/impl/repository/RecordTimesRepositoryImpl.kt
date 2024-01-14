package com.titi.app.data.time.impl.repository

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.data.time.api.model.RecordTimesRepositoryModel
import com.titi.app.data.time.impl.local.RecordTimesDataStore
import com.titi.app.data.time.impl.mapper.toLocalModel
import com.titi.app.data.time.impl.mapper.toRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RecordTimesRepositoryImpl @Inject constructor(
    private val recordTimesDataStore: RecordTimesDataStore,
) : RecordTimesRepository {
    override suspend fun setRecordTimes(recordTimesRepositoryModel: RecordTimesRepositoryModel) {
        recordTimesDataStore.setRecordTimes(
            recordTimesEntity = recordTimesRepositoryModel.toLocalModel(),
        )
    }

    override suspend fun getRecordTimes(): RecordTimesRepositoryModel? {
        return recordTimesDataStore.getRecordTimes()?.toRepositoryModel()
    }

    override fun getRecordTimesFlow(): Flow<RecordTimesRepositoryModel?> {
        return recordTimesDataStore.getRecordTimesFlow().map { it?.toRepositoryModel() }
    }
}
