package com.titi.app.feature.time.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.app.feature.time.ui.timer.TimerScreen

const val TIMER_SCREEN = "timer"
const val TIMER_ROUTE = TIMER_SCREEN
const val TIMER_FINISH_KEY = "isFinish"

const val STOPWATCH_SCREEN = "stopWatch"
const val STOPWATCH_ROUTE = STOPWATCH_SCREEN

fun NavController.navigateToTimer(navOptions: NavOptions) {
    navigate(TIMER_ROUTE, navOptions)
}

fun NavController.navigateToStopWatch(navOptions: NavOptions) {
    navigate(STOPWATCH_ROUTE, navOptions)
}

fun NavGraphBuilder.timeGraph(
    splashResultState: SplashResultState,
    onNavigateToColor: (Int) -> Unit,
    onNavigateToMeasure: (String) -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    onShowResetDailySnackBar: () -> Unit,
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
            onNavigateToMeasure = onNavigateToMeasure,
            onNavigateToDestination = onNavigateToDestination,
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )
    }

    composable(route = STOPWATCH_ROUTE) {
        StopWatchScreen(
            splashResultState = splashResultState,
            onNavigateToColor = { onNavigateToColor(2) },
            onNavigateToMeasure = onNavigateToMeasure,
            onNavigateToDestination = onNavigateToDestination,
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )
    }
}
