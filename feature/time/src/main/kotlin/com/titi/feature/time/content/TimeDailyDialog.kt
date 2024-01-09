package com.titi.feature.time.content

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsInputTimeTextField
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.core.util.getTimeToLong
import com.titi.app.core.designsystem.R

@Composable
fun TimeDailyDialog(
    todayDate: String,
    onPositive: (Long) -> Unit,
    onShowDialog: (Boolean) -> Unit,
) {
    var hour by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }

    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = stringResource(R.string.add_daily_title),
            message = stringResource(R.string.add_daily_message, todayDate),
            positiveText = stringResource(id = R.string.Ok),
            negativeText = stringResource(id = R.string.Cancel),
            onPositive = {
                onPositive(getTimeToLong(hour, minutes, seconds))
            },
        ),
        onShowDialog = onShowDialog
    ) {
        TdsInputTimeTextField(
            modifier = Modifier.padding(horizontal = 15.dp),
            hour = hour,
            onHourChange = { hour = it },
            minutes = minutes,
            onMinutesChange = { minutes = it },
            seconds = seconds,
            onSecondsChange = { seconds = it }
        )
    }
}