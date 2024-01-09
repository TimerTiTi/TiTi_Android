package com.titi.app.data.time.impl.di

import com.titi.app.data.time.api.RecordTimesRepository
import com.titi.app.data.time.impl.repository.RecordTimesRepositoryImpl
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
    fun provideRecordTimesRepository(
        recordTimesRepositoryImpl: RecordTimesRepositoryImpl
    ): RecordTimesRepository

}