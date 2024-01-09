package com.titi.app.data.time.api

import com.titi.app.data.time.api.model.RecordTimesRepositoryModel
import kotlinx.coroutines.flow.Flow

interface RecordTimesRepository {

    suspend fun setRecordTimes(recordTimesRepositoryModel : RecordTimesRepositoryModel)

    suspend fun getRecordTimes() : RecordTimesRepositoryModel?

    fun getRecordTimesFlow() : Flow<RecordTimesRepositoryModel?>

}