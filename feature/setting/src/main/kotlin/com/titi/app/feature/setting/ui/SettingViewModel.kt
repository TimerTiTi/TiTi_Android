package com.titi.app.feature.setting.ui

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.titi.app.data.notification.api.NotificationRepository
import com.titi.app.feature.setting.mapper.toFeatureModel
import com.titi.app.feature.setting.mapper.toRepositoryModel
import com.titi.app.feature.setting.model.SettingActions
import com.titi.app.feature.setting.model.SettingUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingViewModel @AssistedInject constructor(
    @Assisted initialState: SettingUiState,
    private val notificationRepository: NotificationRepository,
) : MavericksViewModel<SettingUiState>(initialState) {

    init {
        viewModelScope.launch {
            notificationRepository.getNotificationFlow()
                .collectLatest {
                    updateSwitch(it.toFeatureModel())
                }
        }
    }

    fun handleUpdateActions(updateActions: SettingActions.Updates) {
        when (updateActions) {
            is SettingActions.Updates.Switch -> {
                viewModelScope.launch {
                    notificationRepository
                        .setNotification(updateActions.switchState.toRepositoryModel())
                }
            }

            is SettingActions.Updates.Version -> updateVersion(updateActions.versionState)
        }
    }

    private fun updateSwitch(switchState: SettingUiState.SwitchState) {
        setState {
            copy(switchState = switchState)
        }
    }

    private fun updateVersion(versionState: SettingUiState.VersionState) {
        setState {
            copy(versionState = versionState)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<SettingViewModel, SettingUiState> {
        override fun create(state: SettingUiState): SettingViewModel
    }

    companion object :
        MavericksViewModelFactory<SettingViewModel, SettingUiState>
        by hiltMavericksViewModelFactory()
}
