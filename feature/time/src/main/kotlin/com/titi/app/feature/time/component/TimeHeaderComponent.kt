package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun TimeHeaderComponent(todayDate: String, textColor: Color, onClickColor: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        TdsText(
            modifier = Modifier.align(Alignment.Center),
            text = todayDate,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 16.sp,
            color = textColor,
        )

        TdsIconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onClickColor,
            size = 32.dp,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.color_selector_icon),
                contentDescription = "setColorIcon",
                tint = Color.Unspecified,
            )
        }
    }
}
