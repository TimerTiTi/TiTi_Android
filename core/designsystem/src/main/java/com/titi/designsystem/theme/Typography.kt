package com.titi.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.titi.designsystem.R

val hgggothicssiProFontFamily = FontFamily(
    Font(R.font.hgggothicssi_pro_00g, FontWeight.Thin),
    Font(R.font.hgggothicssi_pro_20g, FontWeight.ExtraLight),
    Font(R.font.hgggothicssi_pro_40g, FontWeight.Normal),
    Font(R.font.hgggothicssi_pro_60g, FontWeight.SemiBold),
    Font(R.font.hgggothicssi_pro_80g, FontWeight.ExtraBold),
    Font(R.font.hgggothicssi_pro_99g, FontWeight.Black)
)

@Immutable
data class TdsTypography(
    val thinTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 1.sp,
    ),
    val extraLightTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 1.sp,
    ),
    val normalTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 1.sp,
    ),
    val semiBoldTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 1.sp,
    ),
    val extraBoldTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 1.sp,
    ),
    val blackTextStyle: TextStyle = TextStyle(
        fontFamily = hgggothicssiProFontFamily,
        fontWeight = FontWeight.Black,
        fontSize = 1.sp,
    ),
)