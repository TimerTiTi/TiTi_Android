package com.titi.app.feature.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
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
import com.titi.app.core.designsystem.component.TdsNavigationRailItem
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.main.navigation.TiTiNavHost
import com.titi.app.feature.main.navigation.TopLevelDestination

@OptIn(ExperimentalLayoutApi::class)
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color(bottomNavigationColor),
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
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .navigationBarsPadding()
                .statusBarsPadding(),
        ) {
            if (appState.shouldShowNavRail) {
                TiTiRail(
                    modifier = Modifier.safeDrawingPadding(),
                    bottomNavigationColor = bottomNavigationColor,
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                )
            }

            TiTiNavHost(
                modifier = Modifier.fillMaxSize(),
                appState = appState,
                splashResultState = splashResultState,
            )
        }
    }
}

@Composable
private fun TiTiBottomBar(
    bottomNavigationColor: Long,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(
        containerColor = Color(bottomNavigationColor),
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

@Composable
private fun TiTiRail(
    modifier: Modifier = Modifier,
    bottomNavigationColor: Long,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color(bottomNavigationColor),
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            TdsNavigationRailItem(
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
