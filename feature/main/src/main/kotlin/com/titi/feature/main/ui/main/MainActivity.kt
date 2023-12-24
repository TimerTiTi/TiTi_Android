package com.titi.feature.main.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.WindowMetricsCalculator
import com.titi.core.designsystem.theme.TiTiTheme
import com.titi.core.ui.TiTiArgs.MAIN_ARGS
import com.titi.core.ui.TiTiDeepLinkArgs.MEASURE_ARGS
import com.titi.core.ui.TiTiDestinations.MAIN_ROUTE
import com.titi.core.ui.TiTiDestinations.SPLASH_ROUTE
import com.titi.core.ui.TiTiNavigationActions
import com.titi.core.ui.createMeasureUri
import com.titi.core.util.fromJson
import com.titi.core.util.toJson
import com.titi.feature.main.ui.splash.SplashResultState
import com.titi.feature.main.ui.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)
        val widthDp = metrics.bounds.width()
        resources.displayMetrics.density
        val heightDp = metrics.bounds.height() /
                resources.displayMetrics.density

        val splashScreen = installSplashScreen()

        setContent {
            TiTiTheme {
                MainNavGraph(
                    onReady = {
                        splashScreen.setKeepOnScreenCondition { false }
                    },
                    widthDp = widthDp.dp,
                    heightDp = heightDp.dp,
                )
            }
        }
    }
}

@Composable
fun MainNavGraph(
    onReady: () -> Unit,
    widthDp: Dp,
    heightDp: Dp
) {
    val navController = rememberNavController()
    val titiNavigationActions = remember(navController) {
        TiTiNavigationActions(navController = navController)
    }

    val measureResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { splashResultState ->
                    splashResultState.getStringExtra(MEASURE_ARGS)?.let {
                        titiNavigationActions.navigateToMain(it)
                    }
                }
            }
        }

    Scaffold {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.calculateBottomPadding()),
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
                            titiNavigationActions.navigateToMain(splashResultState.toJson())
                        }
                    }
                )
            }

            composable(
                route = MAIN_ROUTE,
            ) { backstackEntry ->
                val splashResultState = backstackEntry
                    .arguments
                    ?.getString(MAIN_ARGS)
                    ?.fromJson<SplashResultState>()
                    ?: SplashResultState()

                MainScreen(
                    splashResultState = splashResultState,
                    widthDp = widthDp,
                    heightDp = heightDp
                )
            }
        }
    }
}