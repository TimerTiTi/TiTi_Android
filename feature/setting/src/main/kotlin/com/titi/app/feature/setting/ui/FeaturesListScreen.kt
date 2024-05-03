package com.titi.app.feature.setting.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun FeaturesListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TdsText(
            text = "Features",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 24.sp,
        )
    }
}
