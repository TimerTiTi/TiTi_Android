package com.titi.app.feature.measure.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.titi.app.core.ui.NavigationActions
import com.titi.app.feature.measure.ui.MeasuringScreen

fun NavGraphBuilder.measureGraph(onFinish: (isFinish: Boolean) -> Unit) {
    composable<NavigationActions.Measure> {
        val args = it.toRoute<NavigationActions.Measure>()
        MeasuringScreen(
            splashResultState = args.splashResultState,
            onFinish = onFinish,
        )
    }
}
