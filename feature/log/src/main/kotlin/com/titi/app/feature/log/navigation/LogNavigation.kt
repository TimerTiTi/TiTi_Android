package com.titi.app.feature.log.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.feature.log.ui.LogScreen

private const val LOG_SCREEN = "log"
const val LOG_ROUTE = LOG_SCREEN

fun NavController.navigateToLog(navOptions: NavOptions) {
    navigate(LOG_ROUTE, navOptions)
}

fun NavGraphBuilder.logGraph(onNavigateToDestination: (TopLevelDestination) -> Unit) {
    composable(route = LOG_ROUTE) {
        LogScreen(onNavigateToDestination = onNavigateToDestination)
    }
}
