package com.titi.app.feature.log.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.titi.app.feature.log.ui.LogScreen

private const val LOG_GRAPH_SCREEN = "logGraph"
const val LOG_GRAPH_ROUTE = LOG_GRAPH_SCREEN

private const val LOG_SCREEN = "log"
const val LOG_ROUTE = LOG_SCREEN

fun NavController.navigateToLog(navOptions: NavOptions) {
    navigate(LOG_ROUTE, navOptions)
}

fun NavGraphBuilder.logGraph() {
    navigation(
        route = LOG_GRAPH_ROUTE,
        startDestination = LOG_ROUTE,
    ) {
        composable(route = LOG_ROUTE) {
            LogScreen()
        }
    }
}
