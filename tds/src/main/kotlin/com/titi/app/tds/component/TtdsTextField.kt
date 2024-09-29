package com.titi.app.tds.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.tds.R
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme

@Composable
fun TtdsOutLinedTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    Row(
        modifier = modifier
            .heightIn(min = 42.dp)
            .border(
                width = 1.dp,
                color = TtdsColor.STROKE.getColor(),
                shape = RoundedCornerShape(6.dp),
            )
            .clip(RoundedCornerShape(6.dp))
            .background(TtdsColor.BACKGROUND_TXT_FIELD.getColor())
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f, true)) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE
                    .getTextStyle(14.sp)
                    .copy(TtdsColor.TEXT_MAIN.getColor()),
                keyboardOptions = keyboardOptions,
                onKeyboardAction = onKeyboardAction,
            )

            if (state.text.isBlank()) {
                TtdsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TtdsColor.TEXT_MUTED,
                )
            }
        }

        if (state.text.isNotBlank()) {
            Icon(
                modifier = Modifier.clickable { state.clearText() },
                painter = painterResource(R.drawable.icon_cancel),
                tint = TtdsColor.BTN_CANCEL.getColor(),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun TtdsOutLinedTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
) {
    Row(
        modifier = modifier
            .heightIn(min = 42.dp)
            .border(
                width = 1.dp,
                color = TtdsColor.STROKE.getColor(),
                shape = RoundedCornerShape(6.dp),
            )
            .clip(RoundedCornerShape(6.dp))
            .background(TtdsColor.BACKGROUND_TXT_FIELD.getColor())
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f, true)) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE
                    .getTextStyle(14.sp)
                    .copy(TtdsColor.TEXT_MAIN.getColor()),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = onValueChange,
            )

            if (text.isBlank()) {
                TtdsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TtdsColor.TEXT_MUTED,
                )
            }
        }

        if (text.isNotBlank()) {
            Icon(
                modifier = Modifier.clickable { onClearText() },
                painter = painterResource(R.drawable.icon_cancel),
                tint = TtdsColor.BTN_CANCEL.getColor(),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun TtdsOutLinedTextField(
    modifier: Modifier = Modifier,
    text: TextFieldValue,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (TextFieldValue) -> Unit,
    onClearText: () -> Unit,
) {
    Row(
        modifier = modifier
            .heightIn(min = 42.dp)
            .border(
                width = 1.dp,
                color = TtdsColor.STROKE.getColor(),
                shape = RoundedCornerShape(6.dp),
            )
            .clip(RoundedCornerShape(6.dp))
            .background(TtdsColor.BACKGROUND_TXT_FIELD.getColor())
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f, true)) {
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE
                    .getTextStyle(14.sp)
                    .copy(TtdsColor.TEXT_MAIN.getColor()),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = onValueChange,
            )

            if (text.text.isBlank()) {
                TtdsText(
                    modifier = Modifier.fillMaxWidth(),
                    text = placeholder,
                    textStyle = TtdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TtdsColor.TEXT_MUTED,
                )
            }
        }

        if (text.text.isNotBlank()) {
            Icon(
                modifier = Modifier.clickable { onClearText() },
                painter = painterResource(R.drawable.icon_cancel),
                tint = TtdsColor.BTN_CANCEL.getColor(),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun TtdsOutLinedTextFieldPreview() {
    TtdsTheme {
        TtdsOutLinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            state = TextFieldState(),
            placeholder = "placeholder",
        )
    }
}