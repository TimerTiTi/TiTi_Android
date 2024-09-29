package com.titi.app.feature.time.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.titi.app.core.designsystem.R
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo

@Composable
fun TimeCheckTaskDialog(onShowDialog: (Boolean) -> Unit) {
    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Alert(
            title = stringResource(id = R.string.task_popup_checktitle),
            confirmText = stringResource(id = R.string.common_text_ok),
        ),
        onShowDialog = onShowDialog,
    )
}
