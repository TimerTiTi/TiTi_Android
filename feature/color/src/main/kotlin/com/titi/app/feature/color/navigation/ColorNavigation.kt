package com.titi.app.feature.color.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.titi.app.feature.color.ui.ColorScreen

const val COLOR_GRAPH_MODE_ARG = "recordingMode"
const val COLOR_GRAPH_SCREEN = "colorGraph"
const val COLOR_GRAPH_ROUTE = "$COLOR_GRAPH_SCREEN?$COLOR_GRAPH_MODE_ARG={$COLOR_GRAPH_MODE_ARG}"

private const val COLOR_SCREEN = "color"
private const val COLOR_ROUTE = COLOR_SCREEN

fun NavController.navigateToColorGraph(recordingMode: Int) {
    navigate(route = makeRoute(recordingMode))
}

private fun makeRoute(recordingMode: Int) =
    "$COLOR_GRAPH_SCREEN?$COLOR_GRAPH_MODE_ARG=$recordingMode"

fun NavGraphBuilder.colorGraph(onFinish: () -> Unit) {
    navigation(
        route = COLOR_GRAPH_ROUTE,
        startDestination = COLOR_ROUTE,
        arguments =
        listOf(
            navArgument(COLOR_GRAPH_MODE_ARG) {
                NavType.IntType
            }
        )
    ) {
        composable(route = COLOR_ROUTE) {
            ColorScreen(
                recordingMode = 1,
                onFinish = onFinish
            )
        }
    }
}
