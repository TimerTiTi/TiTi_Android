package com.titi.feature.main.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.core.designsystem.component.TdsNavigationBarItem
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.ui.TiTiBottomNavigationScreen
import com.titi.feature.main.ui.splash.SplashResultState
import com.titi.feature.main.ui.splash.toFeatureTimeModel
import com.titi.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.feature.time.ui.timer.TimerScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = mavericksViewModel(),
    startDestination: Int,
    splashResultState: SplashResultState,
    isFinish: Boolean,
    widthDp: Dp,
    heightDp: Dp,
    onNavigateToColor: (Int) -> Unit,
    onNavigateToMeasure: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.updateBottomNavigationPosition(startDestination)
    }

    val navController = rememberNavController()

    val items = listOf(
        TiTiBottomNavigationScreen.Timer,
        TiTiBottomNavigationScreen.StopWatch
    )

    val uiState by viewModel.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = when (uiState.bottomNavigationPosition) {
                    1 -> Color(uiState.timeColor.timerBackgroundColor)
                    2 -> Color(uiState.timeColor.stopwatchBackgroundColor)
                    else -> TdsColor.backgroundColor.getColor()
                },
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEachIndexed() { index, screen ->
                    TdsNavigationBarItem(
                        label = { Text(text = screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            viewModel.updateBottomNavigationPosition(index + 1)
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.drawableResourceId),
                                contentDescription = screen.route,
                            )
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .background(TdsColor.backgroundColor.getColor()),
            navController = navController,
            startDestination = if (startDestination == 1) {
                TiTiBottomNavigationScreen.Timer.route
            } else {
                TiTiBottomNavigationScreen.StopWatch.route
            }
        ) {
            composable(TiTiBottomNavigationScreen.Timer.route) {
                TimerScreen(
                    splashResultState = splashResultState.toFeatureTimeModel(),
                    isFinish = isFinish,
                    widthDp = widthDp,
                    heightDp = heightDp,
                    onNavigateToColor = { onNavigateToColor(1) },
                    onNavigateToMeasure = onNavigateToMeasure
                )
            }
            composable(TiTiBottomNavigationScreen.StopWatch.route) {
                StopWatchScreen(
                    splashResultState = splashResultState.toFeatureTimeModel(),
                    widthDp = widthDp,
                    heightDp = heightDp,
                    onNavigateToColor = { onNavigateToColor(2) },
                    onNavigateToMeasure = onNavigateToMeasure
                )
            }
        }
    }
}