package com.titi.app.feature.main.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.titi.app.core.util.toJson
import com.titi.app.feature.color.ui.ColorActivity
import com.titi.app.feature.main.ui.SplashResultState
import com.titi.app.feature.main.ui.TiTiAppState
import com.titi.app.feature.main.ui.toFeatureTimeModel
import com.titi.app.feature.measure.navigation.measuringGraph
import com.titi.app.feature.measure.navigation.navigateToMeasuringGraph
import com.titi.app.feature.time.navigation.STOPWATCH_SCREEN
import com.titi.app.feature.time.navigation.TIMER_FINISH_KEY
import com.titi.app.feature.time.navigation.TIMER_SCREEN
import com.titi.app.feature.time.navigation.TIME_GRAPH_ROUTE
import com.titi.app.feature.time.navigation.timeGraph

@Composable
fun TiTiNavHost(
    splashResultState: SplashResultState,
    appState: TiTiAppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (splashResultState.recordTimes.recording) {
            navController.navigateToMeasuringGraph(splashResultState.toJson())
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TIME_GRAPH_ROUTE,
    ) {
        timeGraph(
            startDestination = if (splashResultState.recordTimes.recordingMode == 1) {
                TIMER_SCREEN
            } else {
                STOPWATCH_SCREEN
            },
            splashResultState = splashResultState.toFeatureTimeModel(),
            onNavigateToColor = {
                val intent = Intent(context, ColorActivity::class.java).apply {
                    putExtra("recordingMode", it)
                }
                context.startActivity(intent)
            },
            onNavigateToMeasure = navController::navigateToMeasuringGraph,
            nestedGraphs = {
                measuringGraph(
                    onFinish = {
                        navController
                            .previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(TIMER_FINISH_KEY, it)
                        navController.popBackStack()
                    },
                )
            },
        )
    }
}
