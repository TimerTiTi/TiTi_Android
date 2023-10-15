package com.titi.data.time.repository.impl

import com.titi.data.time.local.RecordTimesDataStore
import com.titi.data.time.mapper.toLocalModel
import com.titi.data.time.mapper.toRepositoryModel
import com.titi.data.time.repository.api.RecordTimesRepository
import com.titi.data.time.repository.model.RecordTimes
import javax.inject.Inject

internal class RecordTimesRepositoryImpl @Inject constructor(
    private val recordTimesDataStore: RecordTimesDataStore
) : RecordTimesRepository {
    override suspend fun setRecordTimes(recordTimes: RecordTimes) {
        recordTimesDataStore.setRecordTimes(recordTimes = recordTimes.toLocalModel())
    }

    override suspend fun getRecordTimes(): RecordTimes? {
        return recordTimesDataStore.getRecordTimes()?.toRepositoryModel()
    }

}