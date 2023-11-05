package com.titi.core.designsystem.extension

import androidx.compose.ui.graphics.Color

fun Color.complementary() = Color(
    red = 1F - red,
    green = 1F - green,
    blue = 1F - blue
)