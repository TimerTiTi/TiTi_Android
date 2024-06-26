package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsOutlinedInputTextField(
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    text: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeHolder: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        textStyle = TdsTextStyle
            .SEMI_BOLD_TEXT_STYLE
            .getTextStyle(fontSize = fontSize)
            .copy(
                color = TdsColor.TEXT.getColor(),
                textAlign = TextAlign.Center,
            ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(TdsColor.TEXT.getColor()),
    ) { innerTextField ->
        Box(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = TdsColor.DIVIDER.getColor(),
                    shape = RoundedCornerShape(4.dp),
                )
                .clip(RoundedCornerShape(4.dp))
                .background(TdsColor.TERTIARY_BACKGROUND.getColor()),
            contentAlignment = Alignment.Center,
        ) {
            innerTextField()
            if (text.isBlank()) {
                placeHolder?.let { it() }
            }
        }
    }
}

@Composable
fun TdsOutlinedInputTextField(
    modifier: Modifier = Modifier,
    fontSize: TextUnit,
    text: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeHolder: (@Composable () -> Unit)? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        textStyle = TdsTextStyle
            .SEMI_BOLD_TEXT_STYLE
            .getTextStyle(fontSize = fontSize)
            .copy(textAlign = TextAlign.Center),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(TdsColor.TEXT.getColor()),
    ) { innerTextField ->
        Box(
            modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = TdsColor.DIVIDER.getColor(),
                    shape = RoundedCornerShape(4.dp),
                )
                .clip(RoundedCornerShape(4.dp))
                .background(TdsColor.TERTIARY_BACKGROUND.getColor()),
            contentAlignment = Alignment.Center,
        ) {
            innerTextField()
            if (text.text.isBlank()) {
                placeHolder?.let { it() }
            }
        }
    }
}

@Preview
@Composable
private fun TdsInputTextFieldPreview() {
    TiTiTheme {
        TdsOutlinedInputTextField(
            modifier = Modifier
                .width(60.dp)
                .height(40.dp),
            text = "ABC",
            onValueChange = {},
            fontSize = 22.sp,
        )
    }
}
