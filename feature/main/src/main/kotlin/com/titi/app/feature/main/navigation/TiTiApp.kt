package com.titi.app.feature.main.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.titi.app.core.ui.isTablet
import com.titi.app.feature.main.model.SplashResultState
import com.titi.app.tds.R
import com.titi.app.tds.component.TtdsSmallIcon
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
            onShowResetDailySnackBar = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        startIcon = {
                            var startAnimation by remember { mutableStateOf(false) }
                            val rotation by animateFloatAsState(
                                targetValue = if (startAnimation) 360f else 0f,
                                label = "rotation",
                                animationSpec = tween(
                                    durationMillis = 3000,
                                    delayMillis = 150,
                                ),
                            )

                            LaunchedEffect(Unit) {
                                startAnimation = true
                            }

                            TtdsSmallIcon(
                                modifier = Modifier.rotate(rotation),
                                icon = R.drawable.reset_daily_icon,
                            )
                        },
                        emphasizedMessage = "안녕하세요",
                        message = "반갑습니다",
                        targetDpFromTop = if (configuration.isTablet()) 80.dp else 40.dp,
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
