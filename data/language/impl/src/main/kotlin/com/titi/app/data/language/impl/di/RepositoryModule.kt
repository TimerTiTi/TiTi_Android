package com.titi.app.data.language.impl.di

import com.titi.app.data.language.api.LanguageRepository
import com.titi.app.data.language.impl.repository.LanguageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun provideLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl,
    ): LanguageRepository
}
