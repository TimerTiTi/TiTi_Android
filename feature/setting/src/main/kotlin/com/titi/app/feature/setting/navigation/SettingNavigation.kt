package com.titi.app.feature.setting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titi.app.core.ui.NavigationActions
import com.titi.app.feature.setting.model.SettingActions
import com.titi.app.feature.setting.ui.FeaturesListScreen
import com.titi.app.feature.setting.ui.SettingScreen
import com.titi.app.feature.setting.ui.UpdatesListScreen

fun NavGraphBuilder.settingGraph(
    onNavigationActions: (NavigationActions) -> Unit,
    onNavigateToPlayStore: () -> Unit,
    onNavigateToExternalWeb: (String) -> Unit,
) {
    composable<NavigationActions.Setting> {
        SettingScreen(
            handleNavigateActions = {
                when (it) {
                    SettingActions.Navigates.FeaturesList -> onNavigationActions(NavigationActions.Features)
                    SettingActions.Navigates.PlayStore -> onNavigateToPlayStore()
                    SettingActions.Navigates.UpdatesList -> onNavigationActions(NavigationActions.Updates)
                    is SettingActions.Navigates.ExternalWeb -> onNavigateToExternalWeb(it.url)
                }
            },
            onNavigationActions = onNavigationActions,
        )
    }

    composable<NavigationActions.Features> {
        FeaturesListScreen(onNavigationActions = onNavigationActions)
    }

    composable<NavigationActions.Updates> {
        UpdatesListScreen(onNavigationActions = onNavigationActions)
    }
}
