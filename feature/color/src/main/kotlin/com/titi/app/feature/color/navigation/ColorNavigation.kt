package com.titi.app.feature.color.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.titi.app.feature.color.ui.ColorScreen

const val COLOR_GRAPH_SCREEN = "colorGraph"
const val COLOR_RECORDING_MODE_ARG = "recordingMode"
const val COLOR_GRAPH_ROUTE =
    "$COLOR_GRAPH_SCREEN?$COLOR_RECORDING_MODE_ARG={$COLOR_RECORDING_MODE_ARG}"

const val COLOR_SCREEN = "color"
const val COLOR_ROUTE = COLOR_SCREEN

fun NavGraphBuilder.colorGraph(onFinish: () -> Unit) {
    navigation(
        route = COLOR_GRAPH_ROUTE,
        startDestination = COLOR_ROUTE,
        arguments = listOf(
            navArgument(COLOR_RECORDING_MODE_ARG) {
                NavType.IntType
            },
        ),
    ) {
        composable(route = COLOR_ROUTE) { backstackEntry ->
            val recordingMode = backstackEntry
                .arguments
                ?.getInt(COLOR_RECORDING_MODE_ARG)
                ?: 1

            ColorScreen(
                recordingMode = recordingMode,
                onFinish = onFinish,
            )
        }
    }
}
