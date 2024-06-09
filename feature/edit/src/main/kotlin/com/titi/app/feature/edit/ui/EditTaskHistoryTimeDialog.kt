package com.titi.app.feature.edit.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun EditTaskHistoryTimeDialog(themeColor: TdsColor, onShowDialog: (Boolean) -> Unit) {
    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = "01:00:00",
            positiveText = stringResource(R.string.Ok),
            onPositive = {},
            negativeText = stringResource(R.string.Cancel),
        ),
        onShowDialog = onShowDialog,
    ) {
        Column(modifier = Modifier.width(270.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                TdsText(
                    text = "시작 시간",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                TdsText(
                    text = "종료 시간",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TdsText(
                    modifier = Modifier
                        .width(90.dp)
                        .background(
                            color = themeColor
                                .getColor()
                                .copy(0.5f),
                            shape = RoundedCornerShape(4.dp),
                        )
                        .border(
                            width = 2.dp,
                            color = themeColor.getColor(),
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(vertical = 4.dp),
                    text = "10:00:00",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))

                TdsText(
                    text = "~",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                TdsText(
                    modifier = Modifier
                        .width(90.dp)
                        .background(
                            color = themeColor
                                .getColor()
                                .copy(0.5f),
                            shape = RoundedCornerShape(4.dp),
                        )
                        .border(
                            width = 2.dp,
                            color = themeColor.getColor(),
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(vertical = 4.dp),
                    text = "11:00:00",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
