package com.titi.data.color.impl.di

import android.content.Context
import com.titi.data.color.impl.local.ColorDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Singleton
    @Provides
    fun provideColorDataStore(
        @ApplicationContext context: Context
    ) = ColorDataStore(context)

}