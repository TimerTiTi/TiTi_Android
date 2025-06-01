package com.titi.app.feature.time.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.core.ui.NavigationActions
import com.titi.app.feature.time.model.SplashResultState
import com.titi.app.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.app.feature.time.ui.timer.TimerScreen

const val TIMER_FINISH_KEY = "isFinish"

fun NavGraphBuilder.timeGraph(
    splashResultState: SplashResultState,
    onNavigateToColor: (Int) -> Unit,
    onNavigationActions : (NavigationActions) -> Unit,
    onShowResetDailySnackBar: (String) -> Unit,
) {
    composable<NavigationActions.Timer> { backStackEntry ->
        val isFinish by backStackEntry
            .savedStateHandle
            .getStateFlow(TIMER_FINISH_KEY, splashResultState.isMeasureFinish)
            .collectAsStateWithLifecycle()

        TimerScreen(
            splashResultState = splashResultState,
            isFinish = isFinish,
            onChangeFinishStateFalse = {
                backStackEntry.savedStateHandle[TIMER_FINISH_KEY] = false
            },
            onNavigateToColor = { onNavigateToColor(1) },
            onNavigationActions = onNavigationActions,
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )
    }

    composable<NavigationActions.StopWatch> {
        StopWatchScreen(
            splashResultState = splashResultState,
            onNavigateToColor = { onNavigateToColor(2) },
            onNavigationActions = onNavigationActions,
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )
    }
}
