package com.titi.app.data.language.impl.mapper

import com.titi.app.data.language.api.model.LanguageRepositoryModel
import com.titi.app.data.language.impl.local.model.LanguageEntity

internal fun LanguageRepositoryModel.toLocalModel(): LanguageEntity = LanguageEntity(
    system = system,
    korean = korean,
    english = english,
    china = china,
)
