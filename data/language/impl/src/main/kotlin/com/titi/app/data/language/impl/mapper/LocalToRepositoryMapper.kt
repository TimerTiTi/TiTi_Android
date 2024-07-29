package com.titi.app.data.language.impl.mapper

import com.titi.app.data.language.api.model.LanguageRepositoryModel
import com.titi.app.data.language.impl.local.model.LanguageEntity

internal fun LanguageEntity.toRepositoryModel() = LanguageRepositoryModel(
    system = system,
    korean = korean,
    english = english,
    china = china,
)
