package com.titi.app.feature.main.navigation

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.titi.app.feature.log.navigation.logGraph
import com.titi.app.feature.main.ui.SplashResultState
import com.titi.app.feature.main.ui.TiTiAppState
import com.titi.app.feature.main.ui.toFeatureTimeModel
import com.titi.app.feature.popup.PopUpActivity
import com.titi.app.feature.popup.PopUpActivity.Companion.COLOR_RECORDING_MODE_KEY
import com.titi.app.feature.popup.PopUpActivity.Companion.MEASURE_SPLASH_RESULT_KEY
import com.titi.app.feature.time.navigation.STOPWATCH_SCREEN
import com.titi.app.feature.time.navigation.TIMER_SCREEN
import com.titi.app.feature.time.navigation.TIME_GRAPH_ROUTE
import com.titi.app.feature.time.navigation.timeGraph

@Composable
fun TiTiNavHost(
    splashResultState: SplashResultState,
    appState: TiTiAppState,
    measuringResult: ManagedActivityResultLauncher<Intent, ActivityResult>,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TIME_GRAPH_ROUTE,
    ) {
        timeGraph(
            startDestination = if (splashResultState.recordTimes.recordingMode == 1) {
                TIMER_SCREEN
            } else {
                STOPWATCH_SCREEN
            },
            splashResultState = splashResultState.toFeatureTimeModel(),
            onNavigateToColor = {
                val intent = Intent(context, PopUpActivity::class.java).apply {
                    putExtra(
                        COLOR_RECORDING_MODE_KEY,
                        it,
                    )
                }
                context.startActivity(intent)
            },
            onNavigateToMeasure = {
                val intent = Intent(context, PopUpActivity::class.java).apply {
                    putExtra(
                        MEASURE_SPLASH_RESULT_KEY,
                        it,
                    )
                }

                measuringResult.launch(intent)
            },
        )

        logGraph()
    }
}
