package com.titi.app.tds.component.dialog

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.titi.app.tds.R
import com.titi.app.tds.component.TtdsOutLinedTextField
import com.titi.app.tds.model.TtdsDialogInfo
import kotlinx.coroutines.android.awaitFrame

@Composable
fun AddTaskNameDialog(onPositive: (String) -> Unit, onShowDialog: (Boolean) -> Unit) {
    var taskName by remember { mutableStateOf("") }

    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Confirm(
            title = stringResource(id = R.string.tasks_hint_newtasktitle),
            message = stringResource(id = R.string.tasks_popup_newtaskdesc),
            cancelable = false,
            positiveText = stringResource(id = R.string.common_text_ok),
            onPositive = {
                onPositive(taskName)
            },
            negativeText = stringResource(id = R.string.common_text_cancel),
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

        TtdsOutLinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .focusRequester(addTaskFocusRequester),
            text = taskName,
            placeholder = stringResource(id = R.string.tasks_hint_newtasktitle),
            onValueChange = {
                if (it.length <= 12) {
                    taskName = it
                }
            },
            onClearText = {
                taskName = ""
            },
        )
    }
}