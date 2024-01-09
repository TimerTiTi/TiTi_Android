package com.titi.app.data.color.impl.local

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
import com.titi.app.data.color.impl.local.model.BackgroundColorEntity
import com.titi.app.data.color.impl.local.model.ColorEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ColorDataStore(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setColor(colorEntity: ColorEntity) {
        dataStore.storeValue(COLOR_KEY, colorEntity.toJson())
    }

    suspend fun setBackgroundColors(backgroundColorEntity: BackgroundColorEntity) {
        dataStore.storeValue(BACKGROUND_COLORS_KEY, backgroundColorEntity.toJson())
    }

    suspend fun getColor(): ColorEntity? =
        dataStore.readValue(COLOR_KEY)?.fromJson()

    suspend fun getBackgroundColors(): BackgroundColorEntity? =
        dataStore.readValue(BACKGROUND_COLORS_KEY)?.fromJson()

    fun getColorFlow(): Flow<ColorEntity?> =
        dataStore.readFlowValue(COLOR_KEY).map { it?.fromJson() }

    companion object {

        private const val COLOR_PREF_NAME = "colorPrefName"
        private val COLOR_KEY = stringPreferencesKey("colorKey")
        private val BACKGROUND_COLORS_KEY = stringPreferencesKey("backgroundColorsKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = COLOR_PREF_NAME)
    }

}