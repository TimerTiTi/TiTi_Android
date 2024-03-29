package com.titi.app.feature.time.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun TimeTaskContent(
    isSetTask: Boolean,
    textColor: TdsColor,
    taskName: String,
    onClickTask: () -> Unit,
) {
    if (!isSetTask) {
        OutlinedButton(
            onClick = onClickTask,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, TdsColor.RED.getColor()),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
        ) {
            TdsText(
                text = stringResource(R.string.create_task_text),
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                fontSize = 18.sp,
                color = TdsColor.RED,
            )
        }
    } else {
        OutlinedButton(
            onClick = onClickTask,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, textColor.getColor()),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
        ) {
            TdsText(
                text = taskName,
                textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                fontSize = 18.sp,
                color = textColor,
            )
        }
    }
}
