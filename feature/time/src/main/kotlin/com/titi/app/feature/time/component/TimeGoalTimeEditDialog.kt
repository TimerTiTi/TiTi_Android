package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsInputTimeTextField
import com.titi.app.core.designsystem.model.TdsTime
import com.titi.app.core.util.getTimeToLong
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo

@Composable
fun TimeGoalTimeEditDialog(
    todayDate: String,
    currentTime: TdsTime,
    onPositive: (Long) -> Unit,
    onShowDialog: (Boolean) -> Unit,
) {
    var hour by remember { mutableStateOf(currentTime.hour.toString()) }
    var minutes by remember { mutableStateOf(currentTime.minutes.toString()) }
    var seconds by remember { mutableStateOf(currentTime.seconds.toString()) }

    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Confirm(
            title = stringResource(R.string.modify_text_targettime),
            message = stringResource(R.string.modify_text_targettimedesc, todayDate),
            positiveText = stringResource(id = R.string.common_text_ok),
            negativeText = stringResource(id = R.string.common_text_cancel),
            onPositive = {
                onPositive(getTimeToLong(hour, minutes, seconds))
            },
        ),
        onShowDialog = onShowDialog,
    ) {
        TdsInputTimeTextField(
            modifier = Modifier.padding(horizontal = 15.dp),
            hour = hour,
            onHourChange = { hour = it },
            minutes = minutes,
            onMinutesChange = { minutes = it },
            seconds = seconds,
            onSecondsChange = { seconds = it },
        )
    }
}
