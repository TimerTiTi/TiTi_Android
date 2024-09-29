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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.titi.app.tds.R
import com.titi.app.tds.component.TtdsOutLinedTextField
import com.titi.app.tds.model.TtdsDialogInfo
import kotlinx.coroutines.android.awaitFrame

@Composable
fun EditTaskNameDialog(
    taskName: TextFieldValue,
    onPositive: (TextFieldValue) -> Unit,
    onShowDialog: (Boolean) -> Unit,
) {
    var editTaskName by remember {
        mutableStateOf(taskName)
    }
    val modifyTaskNameFocusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(modifyTaskNameFocusRequester) {
        modifyTaskNameFocusRequester.requestFocus()
        awaitFrame()
        keyboard?.show()
    }

    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Confirm(
            title = stringResource(id = R.string.tasks_popup_edittaskname),
            message = stringResource(id = R.string.tasks_popup_newtaskdesc),
            positiveText = stringResource(id = R.string.common_text_ok),
            onPositive = {
                onPositive(editTaskName)
            },
            negativeText = stringResource(id = R.string.common_text_cancel),
        ),
        onShowDialog = onShowDialog,
    ) {
        TtdsOutLinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .focusRequester(modifyTaskNameFocusRequester),
            text = editTaskName,
            onValueChange = {
                if (it.text.length <= 12) {
                    editTaskName = it
                }
            },
            onClearText = {
                editTaskName = TextFieldValue()
            },
            placeholder = "",
        )
    }
}