package com.titi.app.tds.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.titi.app.tds.component.TtdsText
import com.titi.app.tds.component.TtdsTextButton
import com.titi.app.tds.model.TtdsButtonInfo
import com.titi.app.tds.model.TtdsDialogInfo
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme

@Composable
fun TtdsDialog(
    modifier: Modifier = Modifier,
    ttdsDialogInfo: TtdsDialogInfo,
    onShowDialog: (Boolean) -> Unit,
    bodyContent: (@Composable () -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = {
            ttdsDialogInfo.onDismiss?.invoke()
            onShowDialog(false)
        },
        properties = DialogProperties(
            dismissOnBackPress = ttdsDialogInfo.cancelable,
            dismissOnClickOutside = ttdsDialogInfo.cancelable,
        ),
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = TtdsColor.BACKGROUND_MAIN.getColor(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TtdsText(
                    isNoLocale = false,
                    text = ttdsDialogInfo.title,
                    textStyle = TtdsTextStyle.BOLD_TEXT_STYLE,
                    fontSize = 20.sp,
                    color = TtdsColor.TEXT_MAIN,
                    textAlign = TextAlign.Center,
                )

                ttdsDialogInfo.message?.let { message ->
                    TtdsText(
                        isNoLocale = false,
                        text = message,
                        textStyle = TtdsTextStyle.MEDIUM_TEXT_STYLE,
                        fontSize = 15.sp,
                        color = TtdsColor.TEXT_MAIN,
                        textAlign = TextAlign.Center,
                    )
                }

                if (bodyContent != null) {
                    Spacer(modifier = Modifier.height(14.dp))

                    bodyContent()
                }

                Spacer(modifier = Modifier.height(24.dp))

                when (ttdsDialogInfo) {
                    is TtdsDialogInfo.Confirm -> {
                        TtdsConfirmDialogButtons(
                            ttdsDialogInfo = ttdsDialogInfo,
                            onShowDialog = onShowDialog,
                        )
                    }

                    is TtdsDialogInfo.Alert -> {
                        TtdsAlertDialogButton(
                            ttdsDialogInfo = ttdsDialogInfo,
                            onShowDialog = onShowDialog,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TtdsConfirmDialogButtons(
    ttdsDialogInfo: TtdsDialogInfo.Confirm,
    onShowDialog: (Boolean) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TtdsTextButton(
            modifier = Modifier.weight(1f),
            text = ttdsDialogInfo.negativeText,
            buttonColor = TtdsColor.BTN_MUTED,
            textColor = TtdsColor.TEXT_BTN,
            buttonInfo = TtdsButtonInfo.Small(),
            onClick = {
                ttdsDialogInfo.onNegative?.invoke()
                onShowDialog(false)
            },
        )

        Spacer(modifier = Modifier.width(14.dp))

        TtdsTextButton(
            modifier = Modifier.weight(1f),
            text = ttdsDialogInfo.positiveText,
            buttonColor = TtdsColor.PRIMARY,
            textColor = TtdsColor.TEXT_ACTIVE,
            buttonInfo = TtdsButtonInfo.Small(),
            onClick = {
                ttdsDialogInfo.onPositive.invoke()
                onShowDialog(false)
            },
        )
    }
}

@Composable
private fun TtdsAlertDialogButton(
    ttdsDialogInfo: TtdsDialogInfo.Alert,
    onShowDialog: (Boolean) -> Unit,
) {
    TtdsTextButton(
        modifier = Modifier.fillMaxWidth(),
        text = ttdsDialogInfo.confirmText,
        buttonColor = TtdsColor.PRIMARY,
        textColor = TtdsColor.TEXT_ACTIVE,
        buttonInfo = TtdsButtonInfo.Small(),
        onClick = {
            ttdsDialogInfo.onConfirm?.invoke()
            onShowDialog(false)
        },
    )
}

@Preview
@Composable
private fun TtdsConfirmDialogPreview() {
    TtdsTheme {
        TtdsDialog(
            ttdsDialogInfo = TtdsDialogInfo.Confirm(
                title = "제목",
                message = "메시지",
                positiveText = "확인",
                negativeText = "취소",
                onPositive = {},
                onNegative = {},
            ),
            onShowDialog = {},
        )
    }
}

@Preview
@Composable
private fun TtdsAlertDialogPreview() {
    TtdsTheme {
        TtdsDialog(
            ttdsDialogInfo = TtdsDialogInfo.Alert(
                title = "제목",
                message = "메시지",
                confirmText = "확인",
                onConfirm = {},
            ),
            onShowDialog = {},
        )
    }
}
