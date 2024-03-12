package com.titi.app.data.graph.impl.di

import android.content.Context
import com.titi.app.data.graph.impl.local.GraphCheckedDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Singleton
    @Provides
    fun provideGraphCheckedDataStore(@ApplicationContext context: Context) =
        GraphCheckedDataStore(context)
}
