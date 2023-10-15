package com.titi.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import okio.IOException

suspend inline fun <T : Any> DataStore<Preferences>.readValue(
    key: Preferences.Key<T>,
    defaultValue: T
): T {
    return data.catch { recoverOrThrow(it) }.map { it[key] }.firstOrNull() ?: defaultValue
}

suspend inline fun <T : Any> DataStore<Preferences>.readValue(key: Preferences.Key<T>): T? {
    return data.catch { recoverOrThrow(it) }.map { it[key] }.firstOrNull()
}

fun <T : Any> DataStore<Preferences>.readFlowValue(key: Preferences.Key<T>): Flow<T?> {
    return data.catch { recoverOrThrow(it) }.map { it[key] }
}

suspend inline fun <T : Any> DataStore<Preferences>.storeValue(key: Preferences.Key<T>, value: T?) {
    edit { preferences ->
        if (value == null) {
            preferences.remove(key)
        } else {
            preferences[key] = value
        }
    }
}

suspend fun FlowCollector<Preferences>.recoverOrThrow(throwable: Throwable) {
    if (throwable is IOException) {
        emit(emptyPreferences())
    } else {
        throw throwable
    }
}
