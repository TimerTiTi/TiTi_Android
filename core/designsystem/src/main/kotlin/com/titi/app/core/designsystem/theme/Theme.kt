package com.titi.app.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

val LocalTiTiColors = staticCompositionLocalOf {
    TdsColorsPalette()
}

val LocalTiTiTypography = staticCompositionLocalOf {
    TdsTypography()
}

object TiTiTheme {
    val colors: TdsColorsPalette
        @Composable
        get() = LocalTiTiColors.current

    val textStyle: TdsTypography
        @Composable
        get() = LocalTiTiTypography.current
}

@Composable
fun TiTiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    tdsTypography: TdsTypography = TiTiTheme.textStyle,
    content: @Composable () -> Unit,
) {
    val tdsColorsPalette = if (darkTheme) {
        TdsDarkColorsPalette
    } else {
        TdsLightColorsPalette
    }

    CompositionLocalProvider(
        LocalTiTiColors provides tdsColorsPalette,
        LocalDensity provides Density(LocalDensity.current.density, 1f),
        LocalTiTiTypography provides tdsTypography,
    ) {
        content()
    }
}
