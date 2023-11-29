package com.daangn.data.sleep.impl.repository

import com.daangn.data.sleep.api.SleepRepository
import com.daangn.data.sleep.impl.local.SleepDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SleepRepositoryImpl @Inject constructor(
    private val sleepDataStore: SleepDataStore
) : SleepRepository {

    override suspend fun setSleep(isSleep: Boolean) {
        sleepDataStore.setSleep(isSleep)
    }

    override fun getSleepFlow(): Flow<Boolean> =
        sleepDataStore.getSleepFlow().map { it ?: false }

}