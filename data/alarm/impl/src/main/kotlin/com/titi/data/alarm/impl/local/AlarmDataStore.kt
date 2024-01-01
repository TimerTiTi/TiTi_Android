package com.titi.data.alarm.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.core.util.fromJson
import com.titi.core.util.readValue
import com.titi.core.util.storeValue
import com.titi.core.util.toJson
import com.titi.data.alarm.impl.local.model.AlarmsEntity

internal class AlarmDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setAlarms(alarmsEntity: AlarmsEntity) {
        dataStore.storeValue(ALARM_KEY, alarmsEntity.toJson())
    }

    suspend fun getAlarms(): AlarmsEntity? =
        dataStore.readValue(ALARM_KEY)?.fromJson()

    companion object {

        private const val ALARM_PREF_NAME = "alarmPrefName"
        private val ALARM_KEY = stringPreferencesKey("alarmKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            ALARM_PREF_NAME
        )
    }

}