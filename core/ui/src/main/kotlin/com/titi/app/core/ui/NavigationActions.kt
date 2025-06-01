package com.titi.app.core.ui

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
sealed interface NavigationActions {
    @Serializable
    data object Timer : NavigationActions

    @Serializable
    data object StopWatch : NavigationActions

    @Serializable
    data class Measure(
        val splashResultState: String = "",
    ) : NavigationActions

    @Serializable
    data object Log : NavigationActions

    @Serializable
    data object Setting : NavigationActions

    @Serializable
    data object Features : NavigationActions

    @Serializable
    data object Updates : NavigationActions

    @Serializable
    data class WebView(
        val title: String = "",
        val url: String = "",
    ) : NavigationActions

    @Serializable
    data class Edit(
        val currentDate: String = LocalDate.now().toString(),
    ) : NavigationActions

    @Serializable
    data object Up : NavigationActions
}