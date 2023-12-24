package com.titi.feature.color.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.titi.feature.color.ui.ColorViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ColorViewModel::class)
    fun colorViewModelFactory(factory: ColorViewModel.Factory): AssistedViewModelFactory<*, *>

}