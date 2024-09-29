package com.titi.app.tds.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TtdsColorsPalette(
    val primary: Color = Color.Unspecified,
    val text: Color = Color.Unspecified,
    val textMain: Color = Color.Unspecified,
    val textBtn: Color = Color.Unspecified,
    val textActive: Color = Color.Unspecified,
    val btn: Color = Color.Unspecified,
)

val TtdsLightColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF007AFF),
    text = Color(0xFF222222),
    textMain = Color(0xFF222222),
    textBtn = Color(0xFFA4A4A4),
    textActive = Color(0xFFFFFFFF),
    btn = Color(0xFFF1F2F4),
)

val TtdsDarkColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF0A84FF),
    text = Color(0xFF222222),
    textMain = Color(0xFFFFFFFF),
    textBtn = Color(0xFFA4A4A4),
    textActive = Color(0xFFFFFFFF),
    btn = Color(0xFF3B3B3B),
)

enum class TtdsColor {
    PRIMARY,
    TEXT,
    TEXT_MAIN,
    TEXT_BTN,
    TEXT_ACTIVE,
    BTN,
    ;

    @Composable
    fun getColor() = when (this) {
        PRIMARY -> TtdsTheme.colors.primary
        TEXT -> TtdsTheme.colors.text
        TEXT_MAIN -> TtdsTheme.colors.textMain
        TEXT_BTN -> TtdsTheme.colors.textBtn
        TEXT_ACTIVE -> TtdsTheme.colors.textActive
        BTN -> TtdsTheme.colors.btn
    }
}
