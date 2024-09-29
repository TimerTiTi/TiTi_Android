package com.titi.app.tds.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.titi.app.tds.model.TtdsButtonInfo
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme

@Composable
fun TtdsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonColor: TtdsColor,
    textColor: TtdsColor,
    buttonInfo: TtdsButtonInfo,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.clip(buttonInfo.shape),
        onClick = onClick,
        enabled = enabled,
        shape = buttonInfo.shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor.getColor(),
            disabledContainerColor = buttonColor.getColor(),
        ),
        contentPadding = buttonInfo.paddingValues,
    ) {
        TtdsText(
            isNoLocale = false,
            text = text,
            textStyle = TtdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = buttonInfo.fontSize,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun TtdsTextButtonPreview() {
    TtdsTheme {
        TtdsTextButton(
            modifier = Modifier.fillMaxWidth(),
            text = "확인",
            buttonColor = TtdsColor.PRIMARY,
            textColor = TtdsColor.TEXT_ACTIVE,
            buttonInfo = TtdsButtonInfo.Small(),
            onClick = {}
        )
    }
}