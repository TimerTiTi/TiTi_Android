package com.titi.app.data.notification.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.fromJson
import com.titi.app.core.util.readFlowValue
import com.titi.app.core.util.storeValue
import com.titi.app.core.util.toJson
import com.titi.app.data.notification.impl.local.model.NotificationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class NotificationDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setNotification(notificationEntity: NotificationEntity) {
        dataStore.storeValue(NOTIFICATION_KEY, notificationEntity.toJson())
    }

    fun getNotificationFlow(): Flow<NotificationEntity?> =
        dataStore.readFlowValue(NOTIFICATION_KEY).map { it?.fromJson() }

    companion object {
        private const val NOTIFICATION_PREF_NAME = "notificationPrefName"
        private val NOTIFICATION_KEY = stringPreferencesKey("notificationKey")

        private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(NOTIFICATION_PREF_NAME)
    }
}
