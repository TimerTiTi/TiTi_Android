package com.titi.app.feature.main.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.ui.TiTiArgs.MAIN_FINISH_ARG
import com.titi.app.core.ui.TiTiArgs.MAIN_START_ARG
import com.titi.app.core.ui.TiTiDestinations.SPLASH_ROUTE
import com.titi.app.core.ui.createColorUri
import com.titi.app.core.ui.createMeasureUri
import com.titi.app.core.util.toJson
import com.titi.app.feature.main.ui.splash.SplashScreen
import com.titi.app.feature.time.navigation.STOPWATCH_SCREEN
import com.titi.app.feature.time.navigation.TIMER_SCREEN
import com.titi.app.feature.time.navigation.makeTimeRoute
import com.titi.app.feature.time.navigation.navigateToTimeGraph
import com.titi.app.feature.time.navigation.timeGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContent {
            TiTiTheme {
                MainNavGraph(
                    onReady = {
                        splashScreen.setKeepOnScreenCondition { false }
                    }
                )
            }
        }
    }
}

@Composable
fun MainNavGraph(
    onReady: () -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            Log.e("MainActivity", isGranted.toString())
        }

    fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(Unit) {
        askNotificationPermission()
    }

    val measureResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { safeData ->

                    navController.navigateToTimeGraph(
                        route = makeTimeRoute(
                            startDestination = if (safeData.getIntExtra(MAIN_START_ARG, 1) == 1) {
                                TIMER_SCREEN
                            } else {
                                STOPWATCH_SCREEN
                            },
                            isFinish = safeData.getBooleanExtra(MAIN_FINISH_ARG, false)
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

    Scaffold {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = SPLASH_ROUTE
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
}