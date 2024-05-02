package com.titi.app.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.titi.app.feature.setting.ui.SettingScreen

private const val SETTING_SCREEN = "setting"
const val SETTING_ROUTE = SETTING_SCREEN

fun NavController.navigateToSetting(navOptions: NavOptions) {
    navigate(SETTING_ROUTE, navOptions)
}

fun NavGraphBuilder.settingGraph() {
    composable(route = SETTING_ROUTE) {
        SettingScreen()
    }
}
