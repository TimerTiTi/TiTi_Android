package com.titi.app.data.daily.impl.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.readValue
import com.titi.app.core.util.storeValue

internal class DailyDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setResetDailyEvent(daily: String) {
        dataStore.storeValue(DAILY_RESET__KEY, daily)
    }

    suspend fun getResetDailyEvent(): String? = dataStore.readValue(DAILY_RESET__KEY)

    companion object {
        private const val DAILY_RESET_PREF_NAME = "dailyPrefName"
        private val DAILY_RESET__KEY = stringPreferencesKey("dailyKey")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = DAILY_RESET_PREF_NAME,
        )
    }
}
