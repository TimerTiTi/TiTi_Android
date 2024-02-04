package com.titi.app.feature.popup.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.titi.app.feature.color.navigation.COLOR_GRAPH_SCREEN
import com.titi.app.feature.color.navigation.COLOR_RECORDING_MODE_ARG
import com.titi.app.feature.color.navigation.colorGraph
import com.titi.app.feature.measure.navigation.MEASURING_GRAPH_SCREEN
import com.titi.app.feature.measure.navigation.MEASURING_GRAPH_SPLASH_ARG
import com.titi.app.feature.measure.navigation.measuringGraph

fun makeColorRoute(recordingMode: Int) =
    "$COLOR_GRAPH_SCREEN?$COLOR_RECORDING_MODE_ARG=$recordingMode"

fun makeMeasuringRoute(splashResultState: String) =
    "$MEASURING_GRAPH_SCREEN?$MEASURING_GRAPH_SPLASH_ARG=$splashResultState"

@Composable
fun PopUpNavigation(
    startDestination: String,
    onColorFinish: () -> Unit,
    onMeasureFinish: (Boolean) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination,
    ) {
        colorGraph(onFinish = onColorFinish)

        measuringGraph(onFinish = onMeasureFinish)
    }
}
