package com.titi.app.feature.main.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.main.navigation.TopLevelDestination
import com.titi.app.feature.time.navigation.STOPWATCH_ROUTE
import com.titi.app.feature.time.navigation.TIMER_ROUTE
import com.titi.app.feature.time.navigation.navigateToStopWatch
import com.titi.app.feature.time.navigation.navigateToTimer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberNiaAppState(
    navController: NavHostController = rememberNavController(),
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
): TiTiAppState {
    return remember(
        navController,
        windowSizeClass,
    ) {
        TiTiAppState(
            navController,
            windowSizeClass,
            coroutineScope,
            getTimeColorFlowUseCase
        )
    }
}

@Stable
class TiTiAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    private val currentDestinationRouteFlow =
        navController.currentBackStackEntryFlow.map { it.destination.route }

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            TIMER_ROUTE -> TopLevelDestination.TIMER
            STOPWATCH_ROUTE -> TopLevelDestination.STOPWATCH
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    private val isTopLevelDestination: Boolean
        @Composable get() = currentTopLevelDestination != null

    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && isTopLevelDestination

    val bottomNavigationColor: StateFlow<Long> =
        getTimeColorFlowUseCase()
            .combine(currentDestinationRouteFlow) { timeColor: TimeColor, route: String? ->
                when (route) {
                    TIMER_ROUTE -> timeColor.timerBackgroundColor
                    STOPWATCH_ROUTE -> timeColor.stopwatchBackgroundColor
                    else -> 0xFFFFFFFF
                }
            }
            .stateIn(
                scope = coroutineScope,
                SharingStarted.WhileSubscribed(),
                initialValue = 0xFFFFFFFF,
            )

    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)
        @Composable get() = if (isTopLevelDestination) {
            {
                fadeIn(tween(0))
            }
        } else {
            {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(200)
                )
            }
        }

    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)
        @Composable get() = if (isTopLevelDestination) {
            {
                fadeOut(tween(0))
            }
        } else {
            {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(200, delayMillis = 200)
                )
            }
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.TIMER -> navController.navigateToTimer(topLevelNavOptions)
            TopLevelDestination.STOPWATCH -> navController.navigateToStopWatch(topLevelNavOptions)
        }
    }

}