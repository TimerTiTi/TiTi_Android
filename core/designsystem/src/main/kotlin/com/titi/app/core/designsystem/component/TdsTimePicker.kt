package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.util.toOnlyTime
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TdsTimePicker(
    state: TimePickerState,
    themeColor: TdsColor,
    localDateTime: LocalDateTime,
    onTimeChanged: (LocalDateTime) -> Unit,
) {
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    if (showTimePicker) {
        TtdsDialog(
            ttdsDialogInfo = TtdsDialogInfo.Confirm(
                title = "",
                positiveText = stringResource(R.string.common_text_ok),
                negativeText = stringResource(R.string.common_text_cancel),
                onPositive = {
                    onTimeChanged(
                        localDateTime.withHour(state.hour).withMinute(state.minute),
                    )
                },
            ),
            onShowDialog = { showTimePicker = it },
        ) {
            TimePicker(state = state)
        }
    }

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
            .padding(vertical = 4.dp)
            .clickable { showTimePicker = true },
        text = localDateTime.toOnlyTime(),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        color = TdsColor.TEXT,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
    )
}
