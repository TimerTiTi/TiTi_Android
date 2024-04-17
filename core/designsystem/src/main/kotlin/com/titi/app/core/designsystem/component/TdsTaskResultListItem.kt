package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTaskResultListItem(
    height: Dp,
    taskName: String,
    taskTotalTime: String,
    color: Color,
    isSpacing: Boolean,
    leftText: String? = null,
) {
    val padding = height / 10
    val fontSize = (height.value / 2).sp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(vertical = padding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leftText != null) {
            TdsText(
                text = leftText,
                textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                fontSize = fontSize,
                color = color,
            )

            Spacer(modifier = Modifier.width(5.dp))
        }

        TdsText(
            modifier = Modifier
                .widthIn(max = 100.dp)
                .background(color = color.copy(alpha = 0.5f))
                .padding(padding),
            text = taskName,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = fontSize,
            color = TdsColor.TEXT,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        if (isSpacing) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.width(3.dp))
        }

        TdsText(
            text = taskTotalTime,
            textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
            fontSize = fontSize,
            color = color,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
private fun TdsTaskResultListItemPreview() {
    TiTiTheme {
        TdsTaskResultListItem(
            height = 40.dp,
            taskName = "수업",
            taskTotalTime = "5:25:30",
            color = TdsColor.D1.getColor(),
            isSpacing = true,
            leftText = "✔",
        )
    }
}
