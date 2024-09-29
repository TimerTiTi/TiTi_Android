package com.titi.app.tds.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.TextUnit
import com.titi.app.tds.R

val pretendardFontFamily =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
    )
val misansFontFamily =
    FontFamily(
        Font(com.titi.app.core.designsystem.R.font.misans_normal, FontWeight.Normal),
        Font(com.titi.app.core.designsystem.R.font.misans_medium, FontWeight.Medium),
        Font(com.titi.app.core.designsystem.R.font.misans_semibold, FontWeight.SemiBold),
        Font(com.titi.app.core.designsystem.R.font.misans_semibold, FontWeight.Bold),
    )

@Immutable
data class TtdsTypography(
    val normalTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
    ),
    val mediumTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
    ),
    val semiBoldTextStyle: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
    ),
    val boldTextStyle: TextStyle = TextStyle(
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
    fun getTextStyle(
        fontSize: TextUnit,
        isChinese: Boolean = false,
        isNoLocale: Boolean = true,
    ): TextStyle {
        val fontFamily = when {
            isChinese -> misansFontFamily
            Locale.current.language == "zh" && !isNoLocale -> misansFontFamily
            else -> pretendardFontFamily
        }

        return when (this) {
            NORMAL_TEXT_STYLE -> TtdsTheme.textStyle.normalTextStyle.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
            )

            MEDIUM_TEXT_STYLE -> TtdsTheme.textStyle.mediumTextStyle.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
            )

            SEMI_BOLD_TEXT_STYLE -> TtdsTheme.textStyle.semiBoldTextStyle.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
            )

            BOLD_TEXT_STYLE -> TtdsTheme.textStyle.boldTextStyle.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
            )
        }
    }
}
