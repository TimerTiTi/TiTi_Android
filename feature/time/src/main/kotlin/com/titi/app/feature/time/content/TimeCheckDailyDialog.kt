package com.titi.app.feature.time.content

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
fun TimeCheckDailyDialog(title: String, onShowDialog: (Boolean) -> Unit) {
    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Alert(
            title = title,
            confirmText = stringResource(id = R.string.Ok),
        ),
        onShowDialog = onShowDialog,
    ) {
        Spacer(modifier = Modifier.height(5.dp))
    }
}
