package com.titi.app.feature.main.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.titi.app.feature.time.navigation.STOPWATCH_ROUTE
import com.titi.app.feature.time.navigation.TIMER_ROUTE

@Stable
class TiTiAppState(
    val navController: NavController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            TIMER_ROUTE -> TopLevelDestination.TIMER
            STOPWATCH_ROUTE -> TopLevelDestination.STOPWATCH
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries
    val isTopLevelDestination: Boolean
        @Composable get() = currentTopLevelDestination != null

    val shouldShowBottomBar: Boolean
        @Composable get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact && isTopLevelDestination
}