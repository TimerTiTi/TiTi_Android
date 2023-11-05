package com.titi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.feature.time.ui.time.TimeScreen
import com.titi.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)
        val widthDp = metrics.bounds.width()
                resources.displayMetrics.density
        val heightDp = metrics.bounds.height() /
                resources.displayMetrics.density

        setContent {
            TiTiTheme {
                MainScreen(
                    widthDp = widthDp.dp,
                    heightDp = heightDp.dp,
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = mavericksViewModel(),
    widthDp: Dp,
    heightDp: Dp
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Timer,
        Screen.StopWatch
    )

    val uiState by viewModel.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = when (uiState.bottomNavigationPosition) {
                    0 -> Color(uiState.timeColor.timerBackgroundColor)
                    1 -> Color(uiState.timeColor.stopwatchBackgroundColor)
                    else -> TdsColor.backgroundColor.getColor()
                },
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEachIndexed() { index, screen ->
                    NavigationBarItem(
                        label = { Text(text = screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            viewModel.updateBottomNavigationPosition(index)
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
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TdsColor.textColor.getColor(),
                            selectedTextColor = TdsColor.textColor.getColor(),
                            indicatorColor = when (uiState.bottomNavigationPosition) {
                                0 -> Color(uiState.timeColor.timerBackgroundColor)
                                1 -> Color(uiState.timeColor.stopwatchBackgroundColor)
                                else -> TdsColor.backgroundColor.getColor()
                            },
                            unselectedIconColor = TdsColor.lightGrayColor.getColor(),
                            unselectedTextColor = TdsColor.lightGrayColor.getColor()
                        )
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
            startDestination = Screen.Timer.route,
        ) {
            composable(Screen.Timer.route) {
                TimeScreen(
                    recordingMode = 1,
                    widthDp = widthDp,
                    heightDp = heightDp,
                )
            }
            composable(Screen.StopWatch.route) {
                TimeScreen(
                    recordingMode = 2,
                    widthDp = widthDp,
                    heightDp = heightDp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    TiTiTheme {
        MainScreen(
            widthDp = 800.dp,
            heightDp = 1200.dp
        )
    }
}

