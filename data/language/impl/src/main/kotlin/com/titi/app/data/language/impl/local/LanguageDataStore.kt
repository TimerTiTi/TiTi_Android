package com.titi.app.data.language.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.fromJson
import com.titi.app.core.util.readFlowValue
import com.titi.app.core.util.storeValue
import com.titi.app.core.util.toJson
import com.titi.app.data.language.impl.local.model.LanguageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LanguageDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setLanguage(languageEntity: LanguageEntity) {
        dataStore.storeValue(LANGUAGE_KEY, languageEntity.toJson())
    }

    fun getLanguageFlow(): Flow<LanguageEntity?> =
        dataStore.readFlowValue(LANGUAGE_KEY).map { it?.fromJson() }

    companion object {
        private const val LANGUAGE_PREF_NAME = "languagePrefName"
        private val LANGUAGE_KEY = stringPreferencesKey("languageKey")

        private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(LANGUAGE_PREF_NAME)
    }
}
