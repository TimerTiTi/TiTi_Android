package com.titi.app.feature.webview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.titi.app.core.ui.NavigationActions

fun NavGraphBuilder.webViewGraph(onNavigationActions: (NavigationActions) -> Unit) {
    composable<NavigationActions.WebView> {
        val args = it.toRoute<NavigationActions.WebView>()
        WebViewScreen(
            title = args.title,
            url = args.url,
            onNavigationActions = onNavigationActions,
        )
    }
}
