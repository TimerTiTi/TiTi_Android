package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.model.TdsDialogInfo

@Composable
fun TimeCheckTaskDialog(onShowDialog: (Boolean) -> Unit) {
    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Alert(
            title = stringResource(id = R.string.task_popup_checktitle),
            confirmText = stringResource(id = R.string.common_text_ok),
        ),
        onShowDialog = onShowDialog,
    ) {
        Spacer(modifier = Modifier.height(5.dp))
    }
}
