package com.titi.app.data.daily.impl.di

import com.titi.app.data.daily.api.DailyRepository
import com.titi.app.data.daily.impl.repository.DailyRepositoryImpl
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
    fun provideDailyRepository(
        dailyRepositoryImpl: DailyRepositoryImpl
    ): DailyRepository

}