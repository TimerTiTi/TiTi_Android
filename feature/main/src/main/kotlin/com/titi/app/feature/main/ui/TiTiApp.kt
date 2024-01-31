package com.titi.app.feature.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.titi.app.core.designsystem.component.TdsNavigationBarItem
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.main.navigation.TiTiNavHost
import com.titi.app.feature.main.navigation.TopLevelDestination

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun TiTiApp(
    splashResultState: SplashResultState,
    windowSizeClass: WindowSizeClass,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    appState: TiTiAppState =
        rememberNiaAppState(
            windowSizeClass = windowSizeClass,
            getTimeColorFlowUseCase = getTimeColorFlowUseCase,
        ),
) {
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

    val bottomNavigationColor by appState.bottomNavigationColor.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                TiTiBottomBar(
                    bottomNavigationColor = bottomNavigationColor,
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
    ) {
        TiTiNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(bottomNavigationColor)),
            appState = appState,
            splashResultState = splashResultState,
        )
    }
}

@Composable
private fun TiTiBottomBar(
    bottomNavigationColor: Long,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    val animateBottomNavigationBackgroundColor by animateColorAsState(
        targetValue = Color(bottomNavigationColor),
        tween(0),
        label = "animateBottomNavigationBackgroundColor",
    )

    NavigationBar(
        containerColor = animateBottomNavigationBackgroundColor,
        tonalElevation = 0.dp,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            TdsNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.iconResourceId),
                        contentDescription = stringResource(id = destination.titleTextId),
                    )
                },
                label = { Text(stringResource(destination.titleTextId)) },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
