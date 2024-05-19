package com.titi.app.feature.webview

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val WEBVIEW_SCREEN = "webview"
const val WEBVIEW_TITLE_ARG = "title"
const val WEBVIEW_URL_ARG = "url"
const val WEBVIEW_ROUTE =
    "$WEBVIEW_SCREEN?$WEBVIEW_TITLE_ARG={$WEBVIEW_TITLE_ARG}&$WEBVIEW_URL_ARG={$WEBVIEW_URL_ARG}"

fun NavController.navigateToWebView(title: String, url: String) {
    navigate("$WEBVIEW_SCREEN?$WEBVIEW_TITLE_ARG=$title&$WEBVIEW_URL_ARG=$url")
}

fun NavGraphBuilder.webViewGraph(onNavigateUp: () -> Unit) {
    composable(
        route = WEBVIEW_ROUTE,
        arguments = listOf(
            navArgument(WEBVIEW_TITLE_ARG) {
                type = NavType.StringType
            },
            navArgument(WEBVIEW_URL_ARG) {
                type = NavType.StringType
            },
        ),
    ) {
        WebViewScreen(
            title = it.arguments?.getString(WEBVIEW_TITLE_ARG) ?: "",
            url = it.arguments?.getString(WEBVIEW_URL_ARG) ?: "",
            onNavigateUp = onNavigateUp,
        )
    }
}
