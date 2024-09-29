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
    val textMuted: Color = Color.Unspecified,
    val btnMuted: Color = Color.Unspecified,
    val btnCancel: Color = Color.Unspecified,
    val stroke: Color = Color.Unspecified,
    val stroke2: Color = Color.Unspecified,
    val backgroundMain: Color = Color.Unspecified,
    val backgroundTxtField: Color = Color.Unspecified,
)

val TtdsLightColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF007AFF),
    text = Color(0xFF222222),
    textMain = Color(0xFF222222),
    textBtn = Color(0xFFA4A4A4),
    textActive = Color(0xFFFFFFFF),
    textMuted = Color(0xFFB0B0B0),
    btnMuted = Color(0xFFF1F2F4),
    btnCancel = Color(0xFFBFC0C3),
    stroke = Color(0xFFF1F2F4),
    stroke2 = Color(0xFFF1F2F4),
    backgroundMain = Color(0xFFFFFFFF),
    backgroundTxtField = Color(0xFFF7F8FA),
)

val TtdsDarkColorsPalette = TtdsColorsPalette(
    primary = Color(0xFF0A84FF),
    text = Color(0xFF222222),
    textMain = Color(0xFFFFFFFF),
    textBtn = Color(0xFFA4A4A4),
    textActive = Color(0xFFFFFFFF),
    textMuted = Color(0xFFA6A6A6),
    btnMuted = Color(0xFF3B3B3B),
    btnCancel = Color(0xFFFFFFFF),
    stroke = Color(0xFF191919),
    stroke2 = Color(0x80F1F2F4),
    backgroundMain = Color(0xFF202021),
    backgroundTxtField = Color(0xFF5B5B5B),
)

enum class TtdsColor {
    PRIMARY,
    TEXT,
    TEXT_MAIN,
    TEXT_BTN,
    TEXT_ACTIVE,
    TEXT_MUTED,
    BTN_MUTED,
    BTN_CANCEL,
    STROKE,
    STROKE2,
    BACKGROUND_MAIN,
    BACKGROUND_TXT_FIELD,
    ;

    @Composable
    fun getColor() = when (this) {
        PRIMARY -> TtdsTheme.colors.primary
        TEXT -> TtdsTheme.colors.text
        TEXT_MAIN -> TtdsTheme.colors.textMain
        TEXT_BTN -> TtdsTheme.colors.textBtn
        TEXT_ACTIVE -> TtdsTheme.colors.textActive
        TEXT_MUTED -> TtdsTheme.colors.textMuted
        BTN_MUTED -> TtdsTheme.colors.btnMuted
        BTN_CANCEL -> TtdsTheme.colors.btnCancel
        STROKE -> TtdsTheme.colors.stroke
        STROKE2 -> TtdsTheme.colors.stroke2
        BACKGROUND_MAIN -> TtdsTheme.colors.backgroundMain
        BACKGROUND_TXT_FIELD -> TtdsTheme.colors.backgroundTxtField
    }
}
