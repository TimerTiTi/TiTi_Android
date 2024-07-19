package com.titi.app.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R

val hgggothicssiProFontFamily =
    FontFamily(
        Font(R.font.hgggothicssi_pro_40g, FontWeight.Normal),
        Font(R.font.hgggothicssi_pro_60g, FontWeight.SemiBold),
        Font(R.font.hgggothicssi_pro_80g, FontWeight.ExtraBold),
    )
val misansFontFamily =
    FontFamily(
        Font(R.font.misans_normal, FontWeight.Normal),
        Font(R.font.misans_medium, FontWeight.SemiBold),
        Font(R.font.misans_semibold, FontWeight.ExtraBold),
    )

@Immutable
data class TdsTypography(
    val normalTextStyle: TextStyle =
        TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 1.sp,
        ),
    val semiBoldTextStyle: TextStyle =
        TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 1.sp,
        ),
    val extraBoldTextStyle: TextStyle =
        TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 1.sp,
        ),
)

enum class TdsTextStyle {
    NORMAL_TEXT_STYLE,
    SEMI_BOLD_TEXT_STYLE,
    EXTRA_BOLD_TEXT_STYLE,
    ;

    @Composable
    fun getTextStyle(fontSize: TextUnit) = when (this) {
        NORMAL_TEXT_STYLE -> {
            val fontFamily = if (Locale.current.language == "zh") {
                misansFontFamily
            } else {
                hgggothicssiProFontFamily
            }
            TiTiTheme.textStyle.normalTextStyle.copy(fontFamily = fontFamily, fontSize = fontSize)
        }
        SEMI_BOLD_TEXT_STYLE -> {
            val fontFamily = if (Locale.current.language == "zh") {
                misansFontFamily
            } else {
                hgggothicssiProFontFamily
            }
            TiTiTheme.textStyle.semiBoldTextStyle.copy(fontFamily = fontFamily, fontSize = fontSize)
        }
        EXTRA_BOLD_TEXT_STYLE -> {
            val fontFamily = if (Locale.current.language == "zh") {
                misansFontFamily
            } else {
                hgggothicssiProFontFamily
            }
            TiTiTheme.textStyle.extraBoldTextStyle.copy(
                fontFamily = fontFamily,
                fontSize = fontSize,
            )
        }
    }
}
