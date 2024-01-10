package com.titi.app.feature.time.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.titi.app.feature.time.SplashResultState
import com.titi.app.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.app.feature.time.ui.timer.TimerScreen

private const val TIME_GRAPH_SCREEN = "timeGraph"
const val TIME_GRAPH_ROUTE = TIME_GRAPH_SCREEN

const val TIMER_SCREEN = "timer"
const val TIMER_ROUTE = "$TIME_GRAPH_SCREEN/$TIMER_SCREEN"
const val TIMER_FINISH_KEY = "isFinish"

const val STOPWATCH_SCREEN = "stopWatch"
const val STOPWATCH_ROUTE = "$TIME_GRAPH_SCREEN/$STOPWATCH_SCREEN"

fun NavController.navigateToTimer(navOptions: NavOptions) {
    navigate(TIMER_ROUTE, navOptions)
}

fun NavController.navigateToStopWatch(navOptions: NavOptions) {
    navigate(STOPWATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.timeGraph(
    startDestination: String,
    splashResultState: SplashResultState,
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onNavigateToColor: (Int) -> Unit,
    onNavigateToMeasure: (String) -> Unit,
) {
    navigation(
        route = TIME_GRAPH_ROUTE,
        startDestination = "$TIME_GRAPH_SCREEN/$startDestination",
    ) {
        composable(route = TIMER_ROUTE) { backStackEntry ->
            val isFinish by backStackEntry
                .savedStateHandle
                .getStateFlow(TIMER_FINISH_KEY, false)
                .collectAsStateWithLifecycle()

            TimerScreen(
                splashResultState = splashResultState,
                isFinish = isFinish,
                onChangeFinishStateFalse = {
                    backStackEntry.savedStateHandle[TIMER_FINISH_KEY] = false
                },
                onNavigateToColor = { onNavigateToColor(1) },
                onNavigateToMeasure = onNavigateToMeasure
            )
        }

        composable(route = STOPWATCH_ROUTE) {
            StopWatchScreen(
                splashResultState = splashResultState,
                onNavigateToColor = { onNavigateToColor(2) },
                onNavigateToMeasure = onNavigateToMeasure
            )
        }

        nestedGraphs()
    }
}