package com.titi.app.data.sleep.impl.repository

import com.titi.app.data.sleep.api.SleepRepository
import com.titi.app.data.sleep.impl.local.SleepDataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SleepRepositoryImpl @Inject constructor(
    private val sleepDataStore: SleepDataStore,
) : SleepRepository {
    override suspend fun setSleep(isSleep: Boolean) {
        sleepDataStore.setSleep(isSleep)
    }

    override fun getSleepFlow(): Flow<Boolean> = sleepDataStore.getSleepFlow().map { it ?: false }
}
