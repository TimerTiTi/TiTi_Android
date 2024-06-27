package com.titi.app.tds.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TtdsColorsPalette(
    val primary: Color = Color.Unspecified,
    val text: Color = Color.Unspecified,
)

val TtdsLightColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF007AFF),
    text = Color(0xFF222222),
)

val TtdsDarkColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF0A84FF),
    text = Color(0xFF222222),
)

enum class TtdsColor {
    PRIMARY,
    TEXT,
    ;

    @Composable
    fun getColor() = when (this) {
        PRIMARY -> TtdsTheme.colors.primary
        TEXT -> TtdsTheme.colors.text
    }
}
