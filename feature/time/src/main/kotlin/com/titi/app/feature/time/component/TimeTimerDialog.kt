package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.util.addTimeToNow
import com.titi.app.core.util.getTimeToLong
import com.titi.app.tds.component.TtdsInputTimeTextField
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo

@Composable
fun TimeTimerDialog(onPositive: (Long) -> Unit, onShowDialog: (Boolean) -> Unit) {
    var hour by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("") }
    var seconds by remember { mutableStateOf("") }
    var setTimerTime by remember { mutableLongStateOf(0) }

    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Confirm(
            title = stringResource(R.string.timer_text_settimertimetitle),
            message = stringResource(
                R.string.timer_popup_finishtime,
                addTimeToNow(setTimerTime),
            ),
            positiveText = stringResource(id = R.string.common_text_ok),
            negativeText = stringResource(id = R.string.common_text_cancel),
            onPositive = {
                onPositive(setTimerTime)
            },
        ),
        onShowDialog = onShowDialog,
    ) {
        TtdsInputTimeTextField(
            modifier = Modifier.padding(horizontal = 15.dp),
            hour = hour,
            onHourChange = {
                hour = it
                setTimerTime = getTimeToLong(hour, minutes, seconds)
            },
            minutes = minutes,
            onMinutesChange = {
                minutes = it
                setTimerTime = getTimeToLong(hour, minutes, seconds)
            },
            seconds = seconds,
            onSecondsChange = {
                seconds = it
                setTimerTime = getTimeToLong(hour, minutes, seconds)
            },
        )
    }
}
