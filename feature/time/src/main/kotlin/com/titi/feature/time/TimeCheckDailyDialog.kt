package com.titi.feature.time

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.core.designsystem.component.TdsDialog
import com.titi.core.designsystem.model.TdsDialogInfo
import com.titi.designsystem.R

@Composable
fun TimeCheckDailyDialog(
    title: String,
    onShowDialog: (Boolean) -> Unit,
) {
    TdsDialog(
        tdsDialogInfo = TdsDialogInfo.Alert(
            title = title,
            confirmText = stringResource(id = R.string.Ok),
        ),
        onShowDialog = onShowDialog
    ) {
        Spacer(modifier = Modifier.height(5.dp))
    }
}