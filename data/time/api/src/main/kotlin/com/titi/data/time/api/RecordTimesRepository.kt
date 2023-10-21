package com.titi.data.time.api

import com.titi.data.time.api.model.RecordTimesRepositoryModel
import kotlinx.coroutines.flow.Flow

interface RecordTimesRepository {

    suspend fun setRecordTimes(recordTimesRepositoryModel : RecordTimesRepositoryModel)

    suspend fun getRecordTimes() : RecordTimesRepositoryModel?

    fun getRecordTimesFlow() : Flow<RecordTimesRepositoryModel?>

}