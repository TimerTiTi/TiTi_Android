package com.titi.app.data.time.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.fromJson
import com.titi.app.core.util.readFlowValue
import com.titi.app.core.util.readValue
import com.titi.app.core.util.storeValue
import com.titi.app.core.util.toJson
import com.titi.app.data.time.impl.local.model.RecordTimesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RecordTimesDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setRecordTimes(recordTimesEntity: RecordTimesEntity) {
        dataStore.storeValue(RECORD_TIMES_KEY, recordTimesEntity.toJson())
    }

    suspend fun getRecordTimes(): RecordTimesEntity? {
        return dataStore.readValue(RECORD_TIMES_KEY)?.fromJson()
    }

    fun getRecordTimesFlow(): Flow<RecordTimesEntity?> {
        return dataStore.readFlowValue(RECORD_TIMES_KEY).map { it?.fromJson() }
    }

    companion object {
        private const val RECORD_TIMES_PREF_NAME = "recordTimesPrefName"
        private val RECORD_TIMES_KEY = stringPreferencesKey("recordTimesKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = RECORD_TIMES_PREF_NAME,
        )
    }
}
