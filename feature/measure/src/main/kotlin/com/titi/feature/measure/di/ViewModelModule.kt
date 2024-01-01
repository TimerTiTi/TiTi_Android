package com.titi.feature.measure.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.titi.feature.measure.ui.MeasuringViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MeasuringViewModel::class)
    fun measuringViewModelFactory(factory: MeasuringViewModel.Factory): AssistedViewModelFactory<*, *>


}