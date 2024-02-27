package com.titi.app.data.graph.impl.di

import com.titi.app.data.graph.api.GraphCheckedRepository
import com.titi.app.data.graph.impl.repository.GraphCheckedRepositoryImpl
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
    fun provideGraphCheckedRepository(
        graphCheckedRepositoryImpl: GraphCheckedRepositoryImpl,
    ): GraphCheckedRepository
}
