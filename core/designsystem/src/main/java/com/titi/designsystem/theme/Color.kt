package com.titi.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TdsColorsPalette(
    val d1: Color = Color.Unspecified,
    val d2: Color = Color.Unspecified,
    val d3: Color = Color.Unspecified,
    val d4: Color = Color.Unspecified,
    val d5: Color = Color.Unspecified,
    val d6: Color = Color.Unspecified,
    val d7: Color = Color.Unspecified,
    val d8: Color = Color.Unspecified,
    val d9: Color = Color.Unspecified,
    val d10: Color = Color.Unspecified,
    val d11: Color = Color.Unspecified,
    val d12: Color = Color.Unspecified,
    val customTextColor: Color = Color.Unspecified,

    val wrongTextFieldColor: Color = Color.Unspecified,
    val timerBackground: Color = Color.Unspecified,
    val stopwatchBackground: Color = Color.Unspecified,
    val warningRedColor: Color = Color.Unspecified,
    val progressTrackColor: Color = Color.Unspecified,
    val startButtonColor: Color = Color.Unspecified,
    val noTaskWarningRedColor: Color = Color.Unspecified,
    val tabBarNonSelectColor: Color = Color.Unspecified,
    val logoColor: Color = Color.Unspecified,
    val loginSignupBackgroundColor: Color = Color.Unspecified,

    val labelColor: Color = Color.Unspecified,
    val placeHolderTextColor: Color = Color.Unspecified,
    val secondaryLabelColor: Color = Color.Unspecified,
    val secondaryBackgroundColor: Color = Color.Unspecified,
    val secondaryGroupedBackgroundColor: Color = Color.Unspecified,
    val backgroundColor: Color = Color.Unspecified,
    val greenColor: Color = Color.Unspecified,
    val groupedBackgroundColor: Color = Color.Unspecified,
    val pinkColor: Color = Color.Unspecified,
    val redColor: Color = Color.Unspecified,
    val yellowColor: Color = Color.Unspecified,
    val tertiaryLabelColor: Color = Color.Unspecified,
    val tertiaryBackgroundColor: Color = Color.Unspecified,
    val tertiaryGroupedBackgroundColor: Color = Color.Unspecified,
    val tintColor: Color = Color.Unspecified,

    val grayColor: Color = Color.Unspecified,
    val blackColor: Color = Color.Unspecified,
    val darkGrayColor: Color = Color.Unspecified,
    val lightGrayColor: Color = Color.Unspecified,
    val whiteColor: Color = Color.Unspecified,
    val clearColor: Color = Color.Unspecified
)

val TdsLightColorsPalette = TdsColorsPalette(
    d1 = Color(0xFF8299E3),
    d2 = Color(0xFF9AC0DA),
    d3 = Color(0xFF97D2C2),
    d4 = Color(0xFFA6DE9A),
    d5 = Color(0xFFFCE672),
    d6 = Color(0xFFFFC568),
    d7 = Color(0xFFFEA97E),
    d8 = Color(0xFFFF9CA1),
    d9 = Color(0xFFD19AD0),
    d10 = Color(0xFFD19AD0),
    d11 = Color(0xFFAF8CD8),
    d12 = Color(0xFF928AEA),
    customTextColor = Color(0xFFFFFFFF),

    wrongTextFieldColor = Color(0xFFEB5743),
    timerBackground = Color(0xFFA5C0E5),
    stopwatchBackground = Color(0xFF8C8FD3),
    warningRedColor = Color(0xFFEA6E65),
    progressTrackColor = Color(0x66555555),
    startButtonColor = Color(0x80FF5E41),
    noTaskWarningRedColor = Color(0xE5EB5743),
    tabBarNonSelectColor = Color(0x33000000),
    logoColor = Color(0xFFA5C0CE),
    loginSignupBackgroundColor = Color(0xFF9EC1E9),

    labelColor = Color(0xFF000000),
    placeHolderTextColor = Color(0x4D3C3C43),
    secondaryLabelColor = Color(0x993C3C43),
    secondaryBackgroundColor = Color(0xFFF2F2F7),
    secondaryGroupedBackgroundColor = Color(0xFFFFFFFF),
    backgroundColor = Color(0xFFFFFFFF),
    greenColor = Color(0xFF34C759),
    groupedBackgroundColor = Color(0xFFF2F2F7),
    pinkColor = Color(0xFFFF2D55),
    redColor = Color(0xFFFF3B30),
    yellowColor = Color(0xFFFFCC00),
    tertiaryLabelColor = Color(0x4D3C3C43),
    tertiaryBackgroundColor = Color(0xFFFFFFFF),
    tertiaryGroupedBackgroundColor = Color(0xFFF2F2F7),
    tintColor = Color(0xFF007AFF),

    grayColor = Color(0xFF8E8E93),
    blackColor = Color(0xFF000000),
    darkGrayColor = Color(0xFF555555),
    lightGrayColor = Color(0xFFAAAAAA),
    whiteColor = Color(0xFFFFFFFF),
    clearColor = Color(0x00000000)
)

val TdsDarkColorsPalette = TdsColorsPalette(
    d1 = Color(0xFF87A6F8),
    d2 = Color(0xFFA7D7F9),
    d3 = Color(0xFFA7FAE8),
    d4 = Color(0xFFA4FBB9),
    d5 = Color(0xFFF8FC95),
    d6 = Color(0xFFEDC38D),
    d7 = Color(0xFFEDA479),
    d8 = Color(0xFFEB827E),
    d9 = Color(0xFFF0ABAD),
    d10 = Color(0xFFCF9AD1),
    d11 = Color(0xFF896FC7),
    d12 = Color(0xFF6379EB),
    customTextColor = Color(0x8C000000),

    wrongTextFieldColor = Color(0xFFEB5743),
    timerBackground = Color(0xFFA5C0E5),
    stopwatchBackground = Color(0xFF8C8FD3),
    warningRedColor = Color(0xFFEA6E65),
    progressTrackColor = Color(0x66555555),
    startButtonColor = Color(0x80FF5E41),
    noTaskWarningRedColor = Color(0xE5EB5743),
    tabBarNonSelectColor = Color(0x33000000),
    logoColor = Color(0xFFA5C0CE),
    loginSignupBackgroundColor = Color(0xFF9EC1E9),

    labelColor = Color(0xFFFFFFFF),
    placeHolderTextColor = Color(0x4DEBEBF5),
    secondaryLabelColor = Color(0x99EBEBF5),
    secondaryBackgroundColor = Color(0xFF1C1C1E),
    secondaryGroupedBackgroundColor = Color(0xFF1C1C1E),
    backgroundColor = Color(0xFF000000),
    greenColor = Color(0xFF30D158),
    groupedBackgroundColor = Color(0xFF000000),
    pinkColor = Color(0xFFFF375F),
    redColor = Color(0xFFFF453A),
    yellowColor = Color(0xFFFFD60A),
    tertiaryLabelColor = Color(0x4DEBEBF5),
    tertiaryBackgroundColor = Color(0xFF2C2C2E),
    tertiaryGroupedBackgroundColor = Color(0xFF2C2C2E),
    tintColor = Color(0xFF0A84FF),

    grayColor = Color(0xFF8E8E93),
    blackColor = Color(0xFF000000),
    darkGrayColor = Color(0xFF555555),
    lightGrayColor = Color(0xFFAAAAAA),
    whiteColor = Color(0xFFFFFFFF),
    clearColor = Color(0x00000000)
)

enum class TdsColor {
    d1,
    d2,
    d3,
    d4,
    d5,
    d6,
    d7,
    d8,
    d9,
    d10,
    d11,
    d12,
    customTextColor,

    wrongTextFieldColor,
    timerBackground,
    stopwatchBackground,
    warningRedColor,
    progressTrackColor,
    startButtonColor,
    noTaskWarningRedColor,
    tabBarNonSelectColor,
    logoColor,
    loginSignupBackgroundColor,

    labelColor,
    placeHolderTextColor,
    secondaryLabelColor,
    secondaryBackgroundColor,
    secondaryGroupedBackgroundColor,
    backgroundColor,
    greenColor,
    groupedBackgroundColor,
    pinkColor,
    redColor,
    yellowColor,
    tertiaryLabelColor,
    tertiaryBackgroundColor,
    tertiaryGroupedBackgroundColor,
    tintColor,

    grayColor,
    blackColor,
    darkGrayColor,
    lightGrayColor,
    whiteColor,
    clearColor;

    @Composable
    fun getColor() = when (this) {
        d1 -> TiTiTheme.colors.d1
        d2 -> TiTiTheme.colors.d2
        d3 -> TiTiTheme.colors.d3
        d4 -> TiTiTheme.colors.d4
        d5 -> TiTiTheme.colors.d5
        d6 -> TiTiTheme.colors.d6
        d7 -> TiTiTheme.colors.d7
        d8 -> TiTiTheme.colors.d8
        d9 -> TiTiTheme.colors.d9
        d10 -> TiTiTheme.colors.d10
        d11 -> TiTiTheme.colors.d11
        d12 -> TiTiTheme.colors.d12
        customTextColor -> TiTiTheme.colors.customTextColor
        wrongTextFieldColor -> TiTiTheme.colors.wrongTextFieldColor
        timerBackground -> TiTiTheme.colors.timerBackground
        stopwatchBackground -> TiTiTheme.colors.stopwatchBackground
        warningRedColor -> TiTiTheme.colors.warningRedColor
        progressTrackColor -> TiTiTheme.colors.progressTrackColor
        startButtonColor -> TiTiTheme.colors.startButtonColor
        noTaskWarningRedColor -> TiTiTheme.colors.noTaskWarningRedColor
        tabBarNonSelectColor -> TiTiTheme.colors.tabBarNonSelectColor
        logoColor -> TiTiTheme.colors.logoColor
        loginSignupBackgroundColor -> TiTiTheme.colors.loginSignupBackgroundColor
        labelColor -> TiTiTheme.colors.labelColor
        placeHolderTextColor -> TiTiTheme.colors.placeHolderTextColor
        secondaryLabelColor -> TiTiTheme.colors.secondaryLabelColor
        secondaryBackgroundColor -> TiTiTheme.colors.secondaryBackgroundColor
        secondaryGroupedBackgroundColor -> TiTiTheme.colors.secondaryGroupedBackgroundColor
        backgroundColor -> TiTiTheme.colors.backgroundColor
        greenColor -> TiTiTheme.colors.greenColor
        groupedBackgroundColor -> TiTiTheme.colors.groupedBackgroundColor
        pinkColor -> TiTiTheme.colors.pinkColor
        redColor -> TiTiTheme.colors.redColor
        yellowColor -> TiTiTheme.colors.yellowColor
        tertiaryLabelColor -> TiTiTheme.colors.tertiaryLabelColor
        tertiaryBackgroundColor -> TiTiTheme.colors.tertiaryBackgroundColor
        tertiaryGroupedBackgroundColor -> TiTiTheme.colors.tertiaryGroupedBackgroundColor
        tintColor -> TiTiTheme.colors.tintColor
        grayColor -> TiTiTheme.colors.grayColor
        blackColor -> TiTiTheme.colors.blackColor
        darkGrayColor -> TiTiTheme.colors.darkGrayColor
        lightGrayColor -> TiTiTheme.colors.lightGrayColor
        whiteColor -> TiTiTheme.colors.whiteColor
        clearColor -> TiTiTheme.colors.clearColor
    }
}

