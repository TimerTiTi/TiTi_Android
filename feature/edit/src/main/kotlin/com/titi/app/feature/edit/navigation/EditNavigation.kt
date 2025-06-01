package com.titi.app.feature.edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.titi.app.core.ui.NavigationActions
import com.titi.app.feature.edit.ui.EditScreen

fun NavGraphBuilder.editGraph(onNavigationActions: (NavigationActions) -> Unit) {
    composable<NavigationActions.Edit> {
        val args = it.toRoute<NavigationActions.Edit>()
        EditScreen(
            currentDate = args.currentDate,
            onNavigationActions = onNavigationActions,
        )
    }
}
