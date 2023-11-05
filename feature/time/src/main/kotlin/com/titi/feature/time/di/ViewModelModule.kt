package com.titi.feature.time.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.titi.feature.time.ui.color.ColorViewModel
import com.titi.feature.time.ui.task.TaskViewModel
import com.titi.feature.time.ui.time.TimeViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TimeViewModel::class)
    fun timeViewModelFactory(factory: TimeViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    fun taskViewModelFactory(factory: TaskViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(ColorViewModel::class)
    fun colorViewModelFactory(factory: ColorViewModel.Factory): AssistedViewModelFactory<*, *>

}