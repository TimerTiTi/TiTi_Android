package com.titi.app.data.language.impl.di

import android.content.Context
import com.titi.app.data.language.impl.local.LanguageDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Provides
    @Singleton
    fun provideLanguageDataStore(@ApplicationContext context: Context) = LanguageDataStore(context)
}
