package com.titi.app.feature.log.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titi.app.core.ui.NavigationActions
import com.titi.app.feature.log.ui.LogScreen

fun NavGraphBuilder.logGraph(onNavigationActions: (NavigationActions) -> Unit) {
    composable<NavigationActions.Log> {
        LogScreen(onNavigationActions = onNavigationActions)
    }
}
