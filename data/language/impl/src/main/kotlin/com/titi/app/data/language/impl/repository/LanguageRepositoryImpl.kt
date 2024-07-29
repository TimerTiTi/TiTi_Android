package com.titi.app.data.language.impl.repository

import com.titi.app.data.language.api.LanguageRepository
import com.titi.app.data.language.api.model.LanguageRepositoryModel
import com.titi.app.data.language.impl.local.LanguageDataStore
import com.titi.app.data.language.impl.mapper.toLocalModel
import com.titi.app.data.language.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LanguageRepositoryImpl @Inject constructor(
    private val languageDataStore: LanguageDataStore,
) : LanguageRepository {
    override suspend fun setLanguage(languageRepositoryModel: LanguageRepositoryModel) {
        languageDataStore.setLanguage(languageRepositoryModel.toLocalModel())
    }

    override fun getLanguageFlow(): Flow<LanguageRepositoryModel?> {
        return languageDataStore.getLanguageFlow()
            .map { it?.toRepositoryModel() }
    }
}
