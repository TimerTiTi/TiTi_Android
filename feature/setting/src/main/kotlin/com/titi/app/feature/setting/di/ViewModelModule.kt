package com.titi.app.feature.setting.di

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.titi.app.feature.setting.ui.FeaturesListViewModel
import com.titi.app.feature.setting.ui.SettingViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
internal interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    fun settingViewModelFactory(factory: SettingViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(FeaturesListViewModel::class)
    fun featuresViewModelFactory(
        factory: FeaturesListViewModel.Factory,
    ): AssistedViewModelFactory<*, *>
}
