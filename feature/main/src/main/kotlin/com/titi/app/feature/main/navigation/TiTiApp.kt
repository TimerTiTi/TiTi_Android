package com.titi.app.feature.main.navigation

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.titi.app.feature.main.model.SplashResultState
import com.titi.app.tds.TtdsSnackbarHost
import com.titi.app.tds.TtdsSnackbarHostState
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
        )
        val scope = rememberCoroutineScope()
        val abc = remember { TtdsSnackbarHostState() }
        Button(modifier = Modifier.align(Alignment.Center), onClick = {
            scope.launch {
                abc.showSnackbar(message = "sadfasfsadfsdf")
            }
        }) {
            Text("sdfasdfsdaf")
        }

        TtdsSnackbarHost(hostState = abc, modifier = Modifier.align(Alignment.TopCenter)) {
            Row { Text(text = "ABC") }
        }
    }
}
