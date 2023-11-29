package com.daangn.data.sleep.api

import kotlinx.coroutines.flow.Flow

interface SleepRepository {

    suspend fun setSleep(isSleep: Boolean)

    fun getSleepFlow(): Flow<Boolean>

}