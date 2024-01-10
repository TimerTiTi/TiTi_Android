package com.titi.app.feature.main.navigation

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.titi.app.core.ui.TiTiArgs
import com.titi.app.core.ui.TiTiDestinations.SPLASH_ROUTE
import com.titi.app.core.ui.createColorUri
import com.titi.app.core.ui.createMeasureUri
import com.titi.app.core.util.toJson
import com.titi.app.feature.main.ui.TiTiAppState
import com.titi.app.feature.main.ui.splash.SplashScreen
import com.titi.app.feature.time.navigation.STOPWATCH_SCREEN
import com.titi.app.feature.time.navigation.TIMER_SCREEN
import com.titi.app.feature.time.navigation.makeTimeRoute
import com.titi.app.feature.time.navigation.navigateToTimeGraph
import com.titi.app.feature.time.navigation.timeGraph

@Composable
fun TiTiNavHost(
    appState: TiTiAppState,
    modifier: Modifier = Modifier,
    startDestination: String = SPLASH_ROUTE,
    onReady: () -> Unit
) {
    val navController = appState.navController
    val context = LocalContext.current

    val measureResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { safeData ->

                    navController.navigateToTimeGraph(
                        route = makeTimeRoute(
                            startDestination = if (safeData.getIntExtra(TiTiArgs.MAIN_START_ARG, 1) == 1) {
                                TIMER_SCREEN
                            } else {
                                STOPWATCH_SCREEN
                            },
                            isFinish = safeData.getBooleanExtra(TiTiArgs.MAIN_FINISH_ARG, false)
                        ),
                        navOptions = navOptions {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    )
                }
            }
        }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = SPLASH_ROUTE) {
            SplashScreen(
                onReady = { splashResultState ->
                    onReady()

                    if (splashResultState.recordTimes.recording) {
                        measureResult.launch(
                            Intent(
                                Intent.ACTION_VIEW,
                                createMeasureUri(splashResultState.toJson())
                            )
                        )
                    } else {
                        navController.navigateToTimeGraph(
                            route = makeTimeRoute(
                                startDestination = if (splashResultState.recordTimes.recordingMode == 1) {
                                    TIMER_SCREEN
                                } else {
                                    STOPWATCH_SCREEN
                                },
                                splashScreenResult = splashResultState.toJson(),
                            ),
                            navOptions = navOptions {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                }
            )
        }

        timeGraph(
            navController = navController,
            onNavigateToColor = { recordingMode ->
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        createColorUri(recordingMode)
                    )
                )
            },
            onNavigateToMeasure = { splashResultStateString ->
                measureResult.launch(
                    Intent(
                        Intent.ACTION_VIEW,
                        createMeasureUri(splashResultStateString)
                    )
                )
            }
        )
    }
}