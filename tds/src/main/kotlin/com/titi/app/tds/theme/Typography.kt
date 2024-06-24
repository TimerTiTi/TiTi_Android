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
        Font(R.font.pretendard_thin, FontWeight.Thin),
        Font(R.font.pretendard_extra_light, FontWeight.ExtraLight),
        Font(R.font.pretendard_light, FontWeight.Light),
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
        Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold),
        Font(R.font.pretendard_black, FontWeight.Black),
    )

@Immutable
data class TtdsTypography(
    val thinTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Thin,
    ),
    val extraLightTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.ExtraLight,
    ),
    val lightTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Light,
    ),
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
    val extraBoldTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.ExtraBold,
    ),
    val blackTextStyle: TextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.Black,
    ),
)

enum class TtdsTextStyle {
    THIN_TEXT_STYLE,
    EXTRA_LIGHT_TEXT_STYLE,
    LIGHT_TEXT_STYLE,
    NORMAL_TEXT_STYLE,
    MEDIUM_TEXT_STYLE,
    SEMI_BOLD_TEXT_STYLE,
    BOLD_TEXT_STYLE,
    EXTRA_BOLD_TEXT_STYLE,
    BLACK_TEXT_STYLE,
    ;

    @Composable
    fun getTextStyle() = when (this) {
        THIN_TEXT_STYLE -> TtdsTheme.textStyle.thinTextStyle
        EXTRA_LIGHT_TEXT_STYLE -> TtdsTheme.textStyle.extraLightTextStyle
        LIGHT_TEXT_STYLE -> TtdsTheme.textStyle.lightTextStyle
        NORMAL_TEXT_STYLE -> TtdsTheme.textStyle.normalTextStyle
        MEDIUM_TEXT_STYLE -> TtdsTheme.textStyle.mediumTextStyle
        SEMI_BOLD_TEXT_STYLE -> TtdsTheme.textStyle.semiBoldTextStyle
        BOLD_TEXT_STYLE -> TtdsTheme.textStyle.boldTextStyle
        EXTRA_BOLD_TEXT_STYLE -> TtdsTheme.textStyle.extraBoldTextStyle
        BLACK_TEXT_STYLE -> TtdsTheme.textStyle.blackTextStyle
    }
}
