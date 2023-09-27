package com.titi.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

val LocalTiTiColors = staticCompositionLocalOf {
    CustomColorsPalette()
}

object TiTiTheme {

    val colors: CustomColorsPalette
        @Composable
        get() = LocalTiTiColors.current

}

@Composable
fun TiTiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val customColorsPalette =
        if (darkTheme) OnDarkCustomColorsPalette
        else OnLightCustomColorsPalette

    CompositionLocalProvider(
        LocalTiTiColors provides customColorsPalette,
        LocalDensity provides Density(LocalDensity.current.density, 1f),
    ) {
        content()
    }

}