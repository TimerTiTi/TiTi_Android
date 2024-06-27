package com.titi.app.tds.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

val LocalTtdsColors = staticCompositionLocalOf {
    TtdsColorsPalette()
}

val LocalTtdsTypography = staticCompositionLocalOf {
    TtdsTypography()
}

object TtdsTheme {
    val colors: TtdsColorsPalette
        @Composable
        get() = LocalTtdsColors.current

    val textStyle: TtdsTypography
        @Composable
        get() = LocalTtdsTypography.current
}

@Composable
fun TtdsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    ttdsTypography: TtdsTypography = TtdsTheme.textStyle,
    content: @Composable () -> Unit,
) {
    val ttdsColorsPalette = if (darkTheme) {
        TtdsDarkColorsPalette
    } else {
        TtdsLightColorsPalette
    }

    CompositionLocalProvider(
        LocalTtdsColors provides ttdsColorsPalette,
        LocalTtdsTypography provides ttdsTypography,
        LocalDensity provides Density(LocalDensity.current.density, 1f),
    ) {
        content()
    }
}
