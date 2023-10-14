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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.feature.time.TimeScreen
import com.titi.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TiTiTheme {
                MainScreen(
                    bottomNavigationBackgroundColor = TdsColor.blueColor
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    bottomNavigationBackgroundColor: TdsColor,
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Timer,
        Screen.StopWatch
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = bottomNavigationBackgroundColor.getColor(),
                tonalElevation = 0.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    NavigationBarItem(
                        modifier = Modifier.background(bottomNavigationBackgroundColor.getColor()),
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
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TdsColor.textColor.getColor(),
                            selectedTextColor = TdsColor.textColor.getColor(),
                            indicatorColor = bottomNavigationBackgroundColor.getColor(),
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
                    backgroundColor = TdsColor.blueColor,
                    recordingMode = 1
                )
            }
            composable(Screen.StopWatch.route) {
                TimeScreen(
                    backgroundColor = TdsColor.blueColor,
                    recordingMode = 2
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
            bottomNavigationBackgroundColor = TdsColor.blueColor
        )
    }
}

