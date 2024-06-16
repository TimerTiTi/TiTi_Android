package com.titi.app.core.designsystem.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.component.TdsOutlinedInputTextField
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.model.TdsDialogInfo
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import kotlinx.coroutines.android.awaitFrame

@Composable
fun AddTaskNameDialog(onPositive: (String) -> Unit, onShowDialog: (Boolean) -> Unit) {
    var taskName by remember { mutableStateOf("") }

    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = stringResource(id = R.string.add_task_title),
            message = stringResource(id = R.string.add_task_message),
            cancelable = false,
            positiveText = stringResource(id = R.string.Ok),
            onPositive = {
                onPositive(taskName)
            },
            negativeText = stringResource(id = R.string.Cancel),
        ),
        onShowDialog = onShowDialog,
    ) {
        val addTaskFocusRequester = remember { FocusRequester() }
        val keyboard = LocalSoftwareKeyboardController.current

        LaunchedEffect(addTaskFocusRequester) {
            addTaskFocusRequester.requestFocus()
            awaitFrame()
            keyboard?.show()
        }

        TdsOutlinedInputTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp)
                .padding(horizontal = 15.dp)
                .focusRequester(addTaskFocusRequester),
            fontSize = 17.sp,
            text = taskName,
            placeHolder = {
                TdsText(
                    text = stringResource(id = R.string.add_task_title),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 17.sp,
                    color = TdsColor.DIVIDER,
                )
            },
            onValueChange = {
                if (it.length <= 12) {
                    taskName = it
                }
            },
        )
    }
}
