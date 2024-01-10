package com.titi.app.feature.time.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsDialog
import com.titi.app.core.designsystem.model.TdsDialogInfo

@Composable
fun TimeColorDialog(
    backgroundColor: Color,
    textColor: Color,
    onNegative: () -> Unit,
    onShowDialog: (Boolean) -> Unit,
    onClickBackgroundColor: () -> Unit,
    onClickTextColor: (Boolean) -> Unit,
) {
    TdsDialog(
        modifier = Modifier.background(color = Color(0xCCFFFFFF)),
        tdsDialogInfo = TdsDialogInfo.Confirm(
            title = stringResource(id = R.string.custom_color),
            positiveText = stringResource(id = R.string.Ok),
            negativeText = stringResource(id = R.string.Cancel),
            onPositive = {},
            onNegative = onNegative,
        ),
        onShowDialog = onShowDialog
    ) {
        ColorSelectContent(
            backgroundColor = backgroundColor,
            textColor = textColor,
            onClickBackgroundColor = {
                onShowDialog(false)
                onClickBackgroundColor()
            },
            onClickTextColor = onClickTextColor
        )
        Spacer(Modifier.height(16.dp))
    }
}