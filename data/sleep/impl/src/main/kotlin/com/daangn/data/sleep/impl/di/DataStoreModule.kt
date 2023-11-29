package com.daangn.data.sleep.impl.di

import android.content.Context
import com.daangn.data.sleep.impl.local.SleepDataStore
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
    fun provideSleepDataStore(
        @ApplicationContext context: Context
    ) = SleepDataStore(context)

}