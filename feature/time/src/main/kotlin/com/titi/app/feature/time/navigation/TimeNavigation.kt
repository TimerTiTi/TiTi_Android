package com.titi.app.feature.time.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.titi.app.core.util.fromJson
import com.titi.app.feature.time.SplashResultState
import com.titi.app.feature.time.ui.stopwatch.StopWatchScreen
import com.titi.app.feature.time.ui.timer.TimerScreen

const val TIME_GRAPH_START_ARG = "startDestination"
const val TIME_GRAPH_SPLASH_ARG = "splashSearchResult"
const val TIME_GRAPH_FINISH_ARG = "isFinish"

const val TIME_GRAPH_SCREEN = "time"
const val TIME_GRAPH_ROUTE =
    "$TIME_GRAPH_SCREEN/{$TIME_GRAPH_START_ARG}?$TIME_GRAPH_SPLASH_ARG={$TIME_GRAPH_SPLASH_ARG}&$TIME_GRAPH_FINISH_ARG={$TIME_GRAPH_FINISH_ARG}"

const val TIMER_SCREEN = "timer"
const val TIMER_ROUTE = "$TIME_GRAPH_SCREEN/$TIMER_SCREEN"
const val STOPWATCH_SCREEN = "stopWatch"
const val STOPWATCH_ROUTE = "$TIME_GRAPH_SCREEN/$STOPWATCH_SCREEN"
fun NavController.navigateToTimeGraph(
    route: String,
    navOptions: NavOptions
) = navigate(route, navOptions)

fun makeTimeRoute(
    startDestination: String,
    splashScreenResult: String? = null,
    isFinish: Boolean = false
) =
    "$TIME_GRAPH_SCREEN/$startDestination?$TIME_GRAPH_SPLASH_ARG=$splashScreenResult&$TIME_GRAPH_FINISH_ARG=$isFinish"

fun NavGraphBuilder.timeGraph(
    navController: NavHostController,
    //  nestedGraphs: NavGraphBuilder.() -> Unit,
    onNavigateToColor: (Int) -> Unit,
    onNavigateToMeasure: (String) -> Unit,
) {
    navigation(
        route = TIME_GRAPH_ROUTE,
        startDestination = "$TIME_GRAPH_ROUTE/{$TIME_GRAPH_START_ARG}",
        arguments = listOf(
            navArgument(TIME_GRAPH_SPLASH_ARG) {
                NavType.StringType
                nullable = true
            },
            navArgument(TIME_GRAPH_FINISH_ARG) {
                NavType.BoolType
                defaultValue = false
            }
        )
    ) {
        composable(route = TIMER_ROUTE) { backstackEntry ->
            val parentBackStackEntry = remember(backstackEntry) {
                navController.getBackStackEntry(TIME_GRAPH_ROUTE)
            }

            val splashResultState = parentBackStackEntry
                .arguments
                ?.getString(TIME_GRAPH_SPLASH_ARG)
                ?.fromJson<SplashResultState>()
                ?: SplashResultState()

            var isFinishState by remember {
                mutableStateOf(
                    parentBackStackEntry
                        .arguments
                        ?.getBoolean(TIME_GRAPH_FINISH_ARG)
                        ?: false
                )
            }

            TimerScreen(
                splashResultState = splashResultState,
                isFinish = isFinishState,
                onChangeFinishStateFalse = { isFinishState = false },
                onNavigateToColor = { onNavigateToColor(1) },
                onNavigateToMeasure = onNavigateToMeasure
            )
        }

        composable(route = STOPWATCH_ROUTE) { backstackEntry ->
            val parentBackStackEntry = remember(backstackEntry) {
                navController.getBackStackEntry(TIME_GRAPH_ROUTE)
            }

            val splashResultState = parentBackStackEntry
                .arguments
                ?.getString(TIME_GRAPH_SPLASH_ARG)
                ?.fromJson<SplashResultState>()
                ?: SplashResultState()

            StopWatchScreen(
                splashResultState = splashResultState,
                onNavigateToColor = { onNavigateToColor(2) },
                onNavigateToMeasure = onNavigateToMeasure
            )
        }

        //    nestedGraphs()
    }
}