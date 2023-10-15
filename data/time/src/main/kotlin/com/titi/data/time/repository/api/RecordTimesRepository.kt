package com.titi.data.time.repository.api

import com.titi.data.time.repository.model.RecordTimes
import kotlinx.coroutines.flow.Flow

interface RecordTimesRepository {

    suspend fun setRecordTimes(recordTimes : RecordTimes)

    suspend fun getRecordTimes() : RecordTimes?

    fun getRecordTimesFlow() : Flow<RecordTimes?>

}