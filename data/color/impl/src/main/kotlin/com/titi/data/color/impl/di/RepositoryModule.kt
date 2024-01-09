package com.titi.data.color.impl.di

import com.titi.app.data.color.api.ColorRepository
import com.titi.data.color.impl.repository.ColorRepositoryImpl
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
    fun provideColorRepository(
        colorRepositoryImpl: ColorRepositoryImpl
    ): ColorRepository

}