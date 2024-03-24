package com.titi.app.data.graph.impl.di

import com.titi.app.data.graph.api.GraphRepository
import com.titi.app.data.graph.impl.repository.GraphRepositoryImpl
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
    fun provideGraphRepository(graphRepositoryImpl: GraphRepositoryImpl): GraphRepository
}
