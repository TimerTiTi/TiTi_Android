package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTaskResultListItem(
    taskName: String,
    taskTotalTime: String,
    color: Color,
    isSpacing: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TdsText(
            modifier = Modifier
                .widthIn(max = 100.dp)
                .background(
                    color = color.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(2.dp),
                )
                .padding(2.dp),
            text = taskName,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 10.sp,
            color = Color.White,
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
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 12.sp,
            color = color,
        )
    }
}

@Preview
@Composable
private fun TdsTaskResultListItemPreview() {
    TiTiTheme {
        TdsTaskResultListItem(
            taskName = "수업",
            taskTotalTime = "5:25:30",
            color = TdsColor.D1.getColor(),
            isSpacing = true,
        )
    }
}
