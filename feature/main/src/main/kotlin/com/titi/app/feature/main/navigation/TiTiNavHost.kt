package com.titi.app.feature.main.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.titi.app.core.ui.NavigationActions
import com.titi.app.core.util.toJson
import com.titi.app.feature.edit.navigation.editGraph
import com.titi.app.feature.log.navigation.logGraph
import com.titi.app.feature.main.model.SplashResultState
import com.titi.app.feature.main.model.toFeatureTimeModel
import com.titi.app.feature.measure.navigation.measureGraph
import com.titi.app.feature.popup.PopUpActivity
import com.titi.app.feature.popup.PopUpActivity.Companion.COLOR_RECORDING_MODE_KEY
import com.titi.app.feature.setting.navigation.settingGraph
import com.titi.app.feature.time.navigation.TIMER_FINISH_KEY
import com.titi.app.feature.time.navigation.timeGraph
import com.titi.app.feature.webview.webViewGraph

@Composable
fun TiTiNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isConfigurationChange: Boolean,
    splashResultState: SplashResultState,
    onShowResetDailySnackBar: (String) -> Unit,
    onNavigationActions: (NavigationActions) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (splashResultState.recordTimes.recording && !isConfigurationChange) {
            onNavigationActions(NavigationActions.Measure(splashResultState.toJson()))
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (splashResultState.recordTimes.recordingMode == 1) {
            NavigationActions.Timer
        } else {
            NavigationActions.StopWatch
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
            onNavigationActions = onNavigationActions,
            onShowResetDailySnackBar = onShowResetDailySnackBar,
        )

        measureGraph(
            onFinish = { isFinish ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(TIMER_FINISH_KEY, isFinish)
                onNavigationActions(NavigationActions.Up)
            },
        )

        logGraph(onNavigationActions = onNavigationActions)

        settingGraph(
            onNavigateToPlayStore = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=com.titi.app".toUri(),
                )

                context.startActivity(intent)
            },
            onNavigationActions = onNavigationActions,
            onNavigateToExternalWeb = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    it.toUri(),
                )

                context.startActivity(intent)
            },
        )

        webViewGraph(onNavigationActions = onNavigationActions)

        editGraph(onNavigationActions = onNavigationActions)
    }
}
