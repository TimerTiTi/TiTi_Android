package com.titi.app.data.language.api

import com.titi.app.data.language.api.model.LanguageRepositoryModel
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    suspend fun setLanguage(languageRepositoryModel: LanguageRepositoryModel)

    fun getLanguageFlow(): Flow<LanguageRepositoryModel?>
}
