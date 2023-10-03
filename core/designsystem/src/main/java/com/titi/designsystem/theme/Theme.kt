package com.titi.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

val LocalTiTiColors = staticCompositionLocalOf {
    TdsColorsPalette()
}

object TiTiTheme {

    val colors: TdsColorsPalette
        @Composable
        get() = LocalTiTiColors.current

}

@Composable
fun TiTiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val tdsColorsPalette =
        if (darkTheme) TdsDarkColorsPalette
        else TdsLightColorsPalette

    CompositionLocalProvider(
        LocalTiTiColors provides tdsColorsPalette,
        LocalDensity provides Density(LocalDensity.current.density, 1f),
    ) {
        content()
    }

}