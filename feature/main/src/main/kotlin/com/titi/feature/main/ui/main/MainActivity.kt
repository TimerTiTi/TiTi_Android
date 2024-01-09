package com.titi.feature.main.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.core.ui.TiTiArgs.MAIN_FINISH_ARG
import com.titi.core.ui.TiTiArgs.MAIN_SPLASH_ARG
import com.titi.core.ui.TiTiArgs.MAIN_START_ARG
import com.titi.core.ui.TiTiDestinations.MAIN_ROUTE
import com.titi.core.ui.TiTiDestinations.SPLASH_ROUTE
import com.titi.core.ui.TiTiNavigationActions
import com.titi.core.ui.createColorUri
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
    val titiNavigationActions = remember(navController) {
        TiTiNavigationActions(navController = navController)
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // TODO: Inform user that that your app will not show notifications.
            }
        }

    fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as ComponentActivity, Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    LaunchedEffect(Unit){
        askNotificationPermission()
    }

    val measureResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { safeData ->
                    titiNavigationActions.navigateToMain(
                        startDestination = safeData.getIntExtra(MAIN_START_ARG, 1),
                        isFinish = safeData.getBooleanExtra(MAIN_FINISH_ARG, false)
                    )
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
                            titiNavigationActions.navigateToMain(
                                startDestination = splashResultState.recordTimes.recordingMode,
                                splashScreenResult = splashResultState.toJson()
                            )
                        }
                    }
                )
            }

            composable(
                route = MAIN_ROUTE,
                arguments = listOf(
                    navArgument(MAIN_START_ARG) {
                        NavType.IntType
                        defaultValue = 1
                    },
                    navArgument(MAIN_SPLASH_ARG) {
                        NavType.StringType
                        nullable = true
                    },
                    navArgument(MAIN_FINISH_ARG) {
                        NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backstackEntry ->
                val startDestination = backstackEntry
                    .arguments
                    ?.getInt(MAIN_START_ARG)
                    ?: 1

                val splashResultState = backstackEntry
                    .arguments
                    ?.getString(MAIN_SPLASH_ARG)
                    ?.fromJson<SplashResultState>()
                    ?: SplashResultState()

                var isFinishState by remember {
                    mutableStateOf(
                        backstackEntry
                            .arguments
                            ?.getBoolean(MAIN_FINISH_ARG)
                            ?: false
                    )
                }

                MainScreen(
                    startDestination = startDestination,
                    splashResultState = splashResultState,
                    isFinish = isFinishState,
                    onChangeFinishStateFalse = {
                        isFinishState = false
                    },
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
}