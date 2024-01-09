package com.titi.data.sleep.impl.di

import com.titi.app.data.sleep.api.SleepRepository
import com.titi.data.sleep.impl.repository.SleepRepositoryImpl
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
    fun provideSleepRepository(
        sleepRepositoryImpl: SleepRepositoryImpl
    ): SleepRepository

}