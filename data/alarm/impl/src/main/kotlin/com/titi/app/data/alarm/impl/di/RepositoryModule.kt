package com.titi.app.data.alarm.impl.di

import android.content.Context
import com.titi.app.data.alarm.api.AlarmRepository
import com.titi.app.data.alarm.impl.local.AlarmDataStore
import com.titi.app.data.alarm.impl.repository.AlarmRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideAlarmRepository(
        @ApplicationContext context: Context,
        alarmDataStore: AlarmDataStore
    ): AlarmRepository =
        AlarmRepositoryImpl(
            context,
            alarmDataStore
        )

}