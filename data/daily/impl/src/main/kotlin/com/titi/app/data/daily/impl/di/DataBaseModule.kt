package com.titi.app.data.daily.impl.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.titi.app.data.daily.impl.local.DailyDataBase
import com.titi.app.data.daily.impl.local.dao.DailyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataBaseModule {

    @Singleton
    @Provides
    fun provideDailyDataBase(@ApplicationContext context: Context): DailyDataBase = databaseBuilder(
        context,
        DailyDataBase::class.java,
        DailyDataBase.DAILY_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDailyDao(dailyDataBase: DailyDataBase): DailyDao =
        dailyDataBase.getDailyDao()

}