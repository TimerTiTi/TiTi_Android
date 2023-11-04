package com.titi.data.color.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.core.util.fromJson
import com.titi.core.util.readFlowValue
import com.titi.core.util.storeValue
import com.titi.core.util.toJson
import com.titi.data.color.impl.local.model.ColorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ColorDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setColor(colorEntity: ColorEntity) {
        dataStore.storeValue(COLOR_KEY, colorEntity.toJson())
    }

    fun getColorFlow(): Flow<ColorEntity?> =
        dataStore.readFlowValue(COLOR_KEY).map { it?.fromJson() }

    companion object {

        private const val COLOR_PREF_NAME = "colorPrefName"
        private val COLOR_KEY = stringPreferencesKey("colorKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = COLOR_PREF_NAME)
    }

}