package com.titi.app.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.titi.app.feature.setting.model.SettingActions
import com.titi.app.feature.setting.ui.FeaturesListScreen
import com.titi.app.feature.setting.ui.SettingScreen
import com.titi.app.feature.setting.ui.UpdatesListScreen

private const val SETTING_SCREEN = "setting"
const val SETTING_ROUTE = SETTING_SCREEN

private const val FEATURES_SCREEN = "features"
const val FEATURES_ROUTE = FEATURES_SCREEN

private const val UPDATES_SCREEN = "updates"
const val UPDATES_ROUTE = UPDATES_SCREEN

fun NavController.navigateToSetting(navOptions: NavOptions) {
    navigate(SETTING_ROUTE, navOptions)
}

fun NavController.navigateToFeatures() {
    navigate(FEATURES_ROUTE)
}

fun NavController.navigateToUpdates() {
    navigate(UPDATES_ROUTE)
}

fun NavGraphBuilder.settingGraph(
    onNavigateToFeatures: () -> Unit,
    onNavigateToUpdates: () -> Unit,
    onNavigateToPlayStore: () -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToWebView: (title: String, url: String) -> Unit,
) {
    composable(route = SETTING_ROUTE) {
        SettingScreen(
            handleNavigateActions = {
                when (it) {
                    SettingActions.Navigates.FeaturesList -> onNavigateToFeatures()
                    SettingActions.Navigates.PlayStore -> onNavigateToPlayStore()
                    SettingActions.Navigates.UpdatesList -> onNavigateToUpdates()
                }
            },
        )
    }

    composable(route = FEATURES_ROUTE) {
        FeaturesListScreen(
            onNavigateUp = onNavigateUp,
            onNavigateWebView = onNavigateToWebView,
        )
    }

    composable(route = UPDATES_ROUTE) {
        UpdatesListScreen(onNavigateUp = onNavigateUp)
    }
}
