package com.titi.app.feature.time.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun TimeTaskComponent(
    isSetTask: Boolean,
    textColor: Color,
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
                text = stringResource(R.string.tasks_text_createtask),
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 18.sp,
                color = TdsColor.RED,
            )
        }
    } else {
        OutlinedButton(
            onClick = onClickTask,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, textColor),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
        ) {
            TdsText(
                text = taskName,
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 18.sp,
                color = textColor,
            )
        }
    }
}
