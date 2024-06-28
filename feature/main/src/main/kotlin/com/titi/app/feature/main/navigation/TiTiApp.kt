package com.titi.app.feature.main.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.titi.app.core.ui.isTablet
import com.titi.app.feature.main.model.SplashResultState
import com.titi.app.tds.R
import com.titi.app.tds.component.TtdsSnackbarHost
import com.titi.app.tds.component.TtdsSnackbarHostState
import kotlinx.coroutines.launch

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TiTiApp(splashResultState: SplashResultState) {
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            Log.e("MainActivity", isGranted.toString())
        }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { TtdsSnackbarHostState() }
    val configuration = LocalConfiguration.current
    val multiple = if (configuration.isTablet()) 2 else 1

    fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LaunchedEffect(Unit) {
        askNotificationPermission()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        TiTiNavHost(
            modifier = Modifier.fillMaxSize(),
            splashResultState = splashResultState,
            onShowResetDailySnackBar = { date ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        startIcon = {
                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(R.raw.reset_daily_lottie),
                            )
                            val progress by animateLottieCompositionAsState(composition)

                            LottieAnimation(
                                modifier = Modifier
                                    .size(22.dp * multiple)
                                    .padding(4.dp * multiple),
                                composition = composition,
                                progress = { progress },
                            )
                        },
                        emphasizedMessage = date,
                        message = "기록 시작!",
                        targetDpFromTop = 40.dp * multiple,
                    )
                }
            },
        )

        TtdsSnackbarHost(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            hostState = snackbarHostState,
        )
    }
}
