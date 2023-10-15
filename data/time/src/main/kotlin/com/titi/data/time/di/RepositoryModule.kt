package com.titi.data.time.di

import com.titi.data.time.repository.api.RecordTimesRepository
import com.titi.data.time.repository.impl.RecordTimesRepositoryImpl
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