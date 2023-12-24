package com.titi.feature.time.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.titi.feature.time.ui.stopwatch.StopWatchViewModel
import com.titi.feature.time.ui.task.TaskViewModel
import com.titi.feature.time.ui.timer.TimerViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    fun taskViewModelFactory(factory: TaskViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(TimerViewModel::class)
    fun timerViewModelFactory(factory: TimerViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(StopWatchViewModel::class)
    fun stopWatchViewModelFactory(factory: StopWatchViewModel.Factory): AssistedViewModelFactory<*, *>

}