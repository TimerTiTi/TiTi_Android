package com.titi.app.data.sleep.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.readFlowValue
import com.titi.app.core.util.storeValue

internal class SleepDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setSleep(isSleep: Boolean) {
        dataStore.storeValue(SLEEP_KEY, isSleep)
    }

    fun getSleepFlow() = dataStore.readFlowValue(SLEEP_KEY)

    companion object {

        private const val SLEEP_PREF_NAME = "sleepPrefName"
        private val SLEEP_KEY = booleanPreferencesKey("sleepKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SLEEP_PREF_NAME)
    }

}