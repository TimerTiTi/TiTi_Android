package com.titi.app.tds.component.dialog

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
import com.titi.app.tds.R
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

//        TdsOutlinedInputTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(26.dp)
//                .padding(horizontal = 15.dp)
//                .focusRequester(addTaskFocusRequester),
//            fontSize = 17.sp,
//            text = taskName,
//            placeHolder = {
//                TdsText(
//                    isNoLocale = false,
//                    text = stringResource(id = R.string.tasks_hint_newtasktitle),
//                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
//                    fontSize = 17.sp,
//                    color = TdsColor.DIVIDER,
//                )
//            },
//            onValueChange = {
//                if (it.length <= 12) {
//                    taskName = it
//                }
//            },
//        )
    }
}