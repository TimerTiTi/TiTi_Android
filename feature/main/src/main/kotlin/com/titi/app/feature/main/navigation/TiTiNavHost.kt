package com.titi.app.feature.main.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.core.util.toJson
import com.titi.app.feature.edit.navigation.editGraph
import com.titi.app.feature.edit.navigation.navigateToEdit
import com.titi.app.feature.log.navigation.logGraph
import com.titi.app.feature.log.navigation.navigateToLog
import com.titi.app.feature.main.model.SplashResultState
import com.titi.app.feature.main.model.toFeatureTimeModel
import com.titi.app.feature.measure.navigation.measureGraph
import com.titi.app.feature.measure.navigation.navigateToMeasure
import com.titi.app.feature.popup.PopUpActivity
import com.titi.app.feature.popup.PopUpActivity.Companion.COLOR_RECORDING_MODE_KEY
import com.titi.app.feature.setting.navigation.navigateToFeatures
import com.titi.app.feature.setting.navigation.navigateToSetting
import com.titi.app.feature.setting.navigation.navigateToUpdates
import com.titi.app.feature.setting.navigation.settingGraph
import com.titi.app.feature.time.navigation.STOPWATCH_SCREEN
import com.titi.app.feature.time.navigation.TIMER_FINISH_KEY
import com.titi.app.feature.time.navigation.TIMER_SCREEN
import com.titi.app.feature.time.navigation.navigateToStopWatch
import com.titi.app.feature.time.navigation.navigateToTimer
import com.titi.app.feature.time.navigation.timeGraph
import com.titi.app.feature.webview.navigateToWebView
import com.titi.app.feature.webview.webViewGraph
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TiTiNavHost(
    modifier: Modifier = Modifier,
    splashResultState: SplashResultState,
    onShowResetDailySnackBar: (String) -> Unit,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (splashResultState.recordTimes.recording) {
            navController.navigateToMeasure(splashResultState.toJson())
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (splashResultState.recordTimes.recordingMode == 1) {
            TIMER_SCREEN
        } else {
            STOPWATCH_SCREEN
        },
    ) {
        timeGraph(
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
                navController.navigateToMeasure(
                    URLEncoder.encode(it, StandardCharsets.UTF_8.toString()),
                )
            },
            onNavigateToDestination = {
                navController.navigateToTopLevelDestination(it)
            },
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )

        measureGraph(
            onFinish = { isFinish ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(TIMER_FINISH_KEY, isFinish)
                navController.navigateUp()
            },
        )

        logGraph(
            onNavigateToDestination = {
                navController.navigateToTopLevelDestination(it)
            },
            onNavigateToEdit = {
                navController.navigateToEdit(it)
            },
        )

        settingGraph(
            onNavigateToFeatures = { navController.navigateToFeatures() },
            onNavigateToUpdates = { navController.navigateToUpdates() },
            onNavigateToPlayStore = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=com.titi.app".toUri(),
                )

                context.startActivity(intent)
            },
            onNavigateUp = { navController.navigateUp() },
            onNavigateToWebView = { title, url ->
                navController.navigateToWebView(
                    title = title,
                    url = url,
                )
            },
            onNavigateToDestination = {
                navController.navigateToTopLevelDestination(it)
            },
            onNavigateToExternalWeb = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    it.toUri(),
                )

                context.startActivity(intent)
            },
        )

        webViewGraph(onNavigateUp = { navController.navigateUp() })

        editGraph(
            onBack = { navController.navigateUp() },
        )
    }
}

fun NavController.navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    val topLevelNavOptions =
        navOptions {
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

    when (topLevelDestination) {
        TopLevelDestination.TIMER -> navigateToTimer(topLevelNavOptions)
        TopLevelDestination.STOPWATCH -> navigateToStopWatch(topLevelNavOptions)
        TopLevelDestination.LOG -> navigateToLog(topLevelNavOptions)
        TopLevelDestination.SETTING -> navigateToSetting(topLevelNavOptions)
    }
}
