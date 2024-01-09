package com.titi.app.feature.main.ui.main

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.app.core.designsystem.component.TdsNavigationBarItem
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.ui.TiTiBottomNavigationScreen
import com.titi.app.feature.main.ui.splash.SplashResultState
import com.titi.app.feature.main.ui.splash.toFeatureTimeModel
import com.titi.app.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.app.feature.time.ui.timer.TimerScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel = mavericksViewModel(),
    startDestination: Int,
    splashResultState: SplashResultState,
    isFinish: Boolean,
    onChangeFinishStateFalse: () -> Unit,
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

                LaunchedEffect(currentDestination) {
                    currentDestination?.let { safeCurrentDestination ->
                        if (safeCurrentDestination.route == TiTiBottomNavigationScreen.Timer.route) {
                            viewModel.updateBottomNavigationPosition(1)
                        } else {
                            viewModel.updateBottomNavigationPosition(2)
                        }
                    }
                }

                items.forEachIndexed { index, screen ->
                    TdsNavigationBarItem(
                        label = { Text(text = screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
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
                    onChangeFinishStateFalse = onChangeFinishStateFalse,
                    onNavigateToColor = { onNavigateToColor(1) },
                    onNavigateToMeasure = onNavigateToMeasure
                )
            }
            composable(TiTiBottomNavigationScreen.StopWatch.route) {
                StopWatchScreen(
                    splashResultState = splashResultState.toFeatureTimeModel(),
                    onNavigateToColor = { onNavigateToColor(2) },
                    onNavigateToMeasure = onNavigateToMeasure
                )
            }
        }
    }
}