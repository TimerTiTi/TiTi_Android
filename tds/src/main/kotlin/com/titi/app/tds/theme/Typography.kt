package com.titi.app.tds.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.titi.app.tds.R

val pretendardFontFamily =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
    )

@Immutable
data class TtdsTypography(
    val normalTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Normal,
    ),
    val mediumTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Medium,
    ),
    val semiBoldTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
    ),
    val boldTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Bold,
    ),
)

enum class TtdsTextStyle {
    NORMAL_TEXT_STYLE,
    MEDIUM_TEXT_STYLE,
    SEMI_BOLD_TEXT_STYLE,
    BOLD_TEXT_STYLE,
    ;

    @Composable
    fun getTextStyle() = when (this) {
        NORMAL_TEXT_STYLE -> TtdsTheme.textStyle.normalTextStyle
        MEDIUM_TEXT_STYLE -> TtdsTheme.textStyle.mediumTextStyle
        SEMI_BOLD_TEXT_STYLE -> TtdsTheme.textStyle.semiBoldTextStyle
        BOLD_TEXT_STYLE -> TtdsTheme.textStyle.boldTextStyle
    }
}
