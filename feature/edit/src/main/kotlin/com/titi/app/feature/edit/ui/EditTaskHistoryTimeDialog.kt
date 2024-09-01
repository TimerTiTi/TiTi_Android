package com.titi.app.feature.edit.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
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
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.component.TdsTimePicker
import com.titi.app.core.designsystem.extension.getTimeString
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.feature.edit.model.DateTimeTaskHistory
import java.time.Duration
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskHistoryTimeDialog(
    themeColor: TdsColor,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    onShowDialog: (Boolean) -> Unit,
    onPositive: (DateTimeTaskHistory) -> Unit,
) {
    var startLocalDateTime by remember {
        mutableStateOf(startDateTime)
    }
    var endLocalDateTime by remember {
        mutableStateOf(endDateTime)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = startLocalDateTime.hour,
        initialMinute = startLocalDateTime.minute,
    )

    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = (Duration.between(startLocalDateTime, endLocalDateTime).toMillis() / 1000)
                .getTimeString(),
            positiveText = stringResource(R.string.common_text_ok),
            onPositive = {
                if (endLocalDateTime > startLocalDateTime) {
                    onPositive(
                        DateTimeTaskHistory(
                            startDateTime = startLocalDateTime,
                            endDateTime = endLocalDateTime,
                        ),
                    )
                }
            },
            negativeText = stringResource(R.string.common_text_cancel),
        ),
        onShowDialog = onShowDialog,
    ) {
        Row {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TdsText(
                    isNoLocale = false,
                    text = stringResource(R.string.editdaily_text_startat),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsTimePicker(
                    state = timePickerState,
                    themeColor = themeColor,
                    localDateTime = startLocalDateTime,
                    onTimeChanged = {
                        startLocalDateTime = it
                        endLocalDateTime = if (it > endLocalDateTime) {
                            it
                        } else {
                            endLocalDateTime
                        }
                    },
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TdsText(
                    isNoLocale = false,
                    text = stringResource(R.string.editdaily_text_endat),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    color = TdsColor.TEXT,
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsTimePicker(
                    state = timePickerState,
                    themeColor = themeColor,
                    localDateTime = endLocalDateTime,
                    onTimeChanged = {
                        startLocalDateTime = if (startLocalDateTime > it) {
                            it
                        } else {
                            startLocalDateTime
                        }
                        endLocalDateTime = it
                    },
                )
            }
        }
    }
}
