package com.titi.data.time.repository.api

import com.titi.data.time.repository.model.RecordTimes

interface RecordTimesRepository {

    suspend fun setRecordTimes(recordTimes : RecordTimes)

    suspend fun getRecordTimes() : RecordTimes?

}