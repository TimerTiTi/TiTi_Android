package com.titi.app.feature.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.AMPMHours
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimePicker
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@Composable
fun EditTaskHistoryTimeDialog(themeColor: TdsColor, onShowDialog: (Boolean) -> Unit) {
    var startPickerValue by remember {
        mutableStateOf(AMPMHours(10, 10, AMPMHours.DayTime.AM))
    }

    var endPickerValue by remember {
        mutableStateOf(AMPMHours(10, 10, AMPMHours.DayTime.AM))
    }

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
                TdsTimePicker(
                    themeColor = themeColor,
                    pickerValue = startPickerValue,
                    onValueChange = { startPickerValue = it },
                )

                Spacer(modifier = Modifier.weight(1f))

                TdsText(
                    text = "~",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                TdsTimePicker(
                    themeColor = themeColor,
                    pickerValue = endPickerValue,
                    onValueChange = { endPickerValue = it },
                )
            }
        }
    }
}
