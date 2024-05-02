package com.titi.app.feature.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.titi.app.domain.color.model.TimeColor
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.log.navigation.LOG_ROUTE
import com.titi.app.feature.log.navigation.navigateToLog
import com.titi.app.feature.main.navigation.TopLevelDestination
import com.titi.app.feature.setting.navigation.SETTING_ROUTE
import com.titi.app.feature.setting.navigation.navigateToSetting
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
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    isSystemDarkTheme: Boolean,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
): TiTiAppState {
    return remember(navController) {
        TiTiAppState(
            navController,
            isSystemDarkTheme,
            coroutineScope,
            getTimeColorFlowUseCase,
        )
    }
}

@Stable
class TiTiAppState(
    val navController: NavHostController,
    isSystemDarkTheme: Boolean,
    coroutineScope: CoroutineScope,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
) {
    val currentDestination: NavDestination?
        @Composable get() =
            navController
                .currentBackStackEntryAsState().value?.destination

    private val currentDestinationRouteFlow =
        navController.currentBackStackEntryFlow.map { it.destination.route }

    private val currentTopLevelDestination: TopLevelDestination?
        @Composable get() =
            when (currentDestination?.route) {
                TIMER_ROUTE -> TopLevelDestination.TIMER
                STOPWATCH_ROUTE -> TopLevelDestination.STOPWATCH
                LOG_ROUTE -> TopLevelDestination.LOG
                SETTING_ROUTE -> TopLevelDestination.SETTING
                else -> null
            }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    private val isTopLevelDestination: Boolean
        @Composable get() = currentTopLevelDestination != null

    val shouldShowBottomBar: Boolean
        @Composable get() = isTopLevelDestination

    val bottomNavigationColor: StateFlow<Long> =
        getTimeColorFlowUseCase()
            .combine(currentDestinationRouteFlow) { timeColor: TimeColor, route: String? ->
                when (route) {
                    TIMER_ROUTE -> timeColor.timerBackgroundColor
                    STOPWATCH_ROUTE -> timeColor.stopwatchBackgroundColor
                    LOG_ROUTE -> if (isSystemDarkTheme) 0xFF000000 else 0xFFFFFFFF
                    SETTING_ROUTE -> if (isSystemDarkTheme) 0xFF000000 else 0xFFFFFFFF
                    else -> 0xFF000000
                }
            }
            .stateIn(
                scope = coroutineScope,
                SharingStarted.WhileSubscribed(),
                initialValue = 0xFF000000,
            )

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions =
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }

        when (topLevelDestination) {
            TopLevelDestination.TIMER -> navController.navigateToTimer(topLevelNavOptions)
            TopLevelDestination.STOPWATCH -> navController.navigateToStopWatch(topLevelNavOptions)
            TopLevelDestination.LOG -> navController.navigateToLog(topLevelNavOptions)
            TopLevelDestination.SETTING -> navController.navigateToSetting(topLevelNavOptions)
        }
    }
}
