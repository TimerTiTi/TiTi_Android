package com.titi.app.feature.measure.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.titi.app.core.util.fromJson
import com.titi.app.feature.measure.SplashResultState
import com.titi.app.feature.measure.ui.MeasuringScreen

private const val MEASURING_GRAPH_SPLASH_ARG = "splashScreenResult"

private const val MEASURING_GRAPH_SCREEN = "measuringGraph"
private const val MEASURING_GRAPH_ROUTE =
    "$MEASURING_GRAPH_SCREEN?$MEASURING_GRAPH_SPLASH_ARG={$MEASURING_GRAPH_SPLASH_ARG}"

private const val MEASURING_SCREEN = "measuring"
private const val MEASURING_ROUTE = MEASURING_SCREEN

fun NavController.navigateToMeasuringGraph(splashResultState: String) {
    navigate(route = makeRoute(splashResultState))
}

private fun makeRoute(splashResultState: String) =
    "$MEASURING_GRAPH_SCREEN?$MEASURING_GRAPH_SPLASH_ARG=$splashResultState"

fun NavGraphBuilder.measuringGraph(onFinish: (Boolean) -> Unit) {
    navigation(
        route = MEASURING_GRAPH_ROUTE,
        startDestination = MEASURING_ROUTE,
        arguments = listOf(
            navArgument(MEASURING_GRAPH_SPLASH_ARG) {
                NavType.StringType
            }
        )
    ) {
        composable(route = MEASURING_ROUTE) { backstackEntry ->
            val splashResultStateFromBackStackEntry = backstackEntry
                .arguments
                ?.getString(MEASURING_GRAPH_SPLASH_ARG)
                ?.fromJson<SplashResultState>()
                ?: SplashResultState()

            MeasuringScreen(
                splashResultState = splashResultStateFromBackStackEntry,
                onFinish = onFinish
            )
        }
    }
}