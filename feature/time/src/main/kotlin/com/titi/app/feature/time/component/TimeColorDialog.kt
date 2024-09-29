package com.titi.app.feature.time.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.tds.component.dialog.TtdsDialog
import com.titi.app.tds.model.TtdsDialogInfo

@Composable
fun TimeColorDialog(
    backgroundColor: Color,
    textColor: Color,
    onNegative: () -> Unit,
    onShowDialog: (Boolean) -> Unit,
    onClickBackgroundColor: () -> Unit,
    onClickTextColor: (Boolean) -> Unit,
) {
    TtdsDialog(
        ttdsDialogInfo = TtdsDialogInfo.Confirm(
            title = stringResource(id = R.string.recordingcolorselector_text_customcolor),
            positiveText = stringResource(id = R.string.common_text_ok),
            negativeText = stringResource(id = R.string.common_text_cancel),
            onPositive = {
                onShowDialog(false)
            },
            onNegative = onNegative,
        ),
        onShowDialog = onShowDialog,
    ) {
        ColorSelectComponent(
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClickBackgroundColor = {
                onClickBackgroundColor()
            },
            onClickTextColor = onClickTextColor,
        )
        Spacer(Modifier.height(16.dp))
    }
}
