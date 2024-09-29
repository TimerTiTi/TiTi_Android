package com.titi.app.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

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