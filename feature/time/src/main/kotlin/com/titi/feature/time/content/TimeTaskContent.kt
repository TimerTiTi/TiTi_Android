package com.titi.feature.time.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.core.designsystem.component.TdsText
import com.titi.core.designsystem.theme.TdsColor
import com.titi.core.designsystem.theme.TdsTextStyle
import com.titi.designsystem.R

@Composable
fun TimeTaskContent(
    isSetTask : Boolean,
    textColor : TdsColor,
    taskName : String,
    onClickTask : () -> Unit,
){
    if (!isSetTask) {
        OutlinedButton(
            onClick = onClickTask,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, TdsColor.redColor.getColor()),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp)
        ) {
            TdsText(
                text = stringResource(R.string.create_task_text),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 18.sp,
                color = TdsColor.redColor,
            )
        }
    } else {
        OutlinedButton(
            onClick = onClickTask,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(2.dp, textColor.getColor()),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp)
        ) {
            TdsText(
                text = taskName,
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 18.sp,
                color = textColor
            )
        }
    }
}