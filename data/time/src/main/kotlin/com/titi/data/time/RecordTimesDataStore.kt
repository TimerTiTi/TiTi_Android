package com.titi.data.time

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.core.util.fromJson
import com.titi.core.util.readValue
import com.titi.core.util.storeValue
import com.titi.core.util.toJson
import com.titi.data.time.model.RecordTimes

class RecordTimesDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setRecordTimes(recordTimes: RecordTimes) {
        dataStore.storeValue(RECORD_TIMES_KEY, recordTimes.toJson())
    }

    suspend fun getRecordTimes(): RecordTimes? {
        return dataStore.readValue(RECORD_TIMES_KEY)?.fromJson()
    }

    companion object {

        private const val RECORD_TIMES_PREF_NAME = "recordTimesPrefName"
        private val RECORD_TIMES_KEY = stringPreferencesKey("recordTimesKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = RECORD_TIMES_PREF_NAME)
    }

}