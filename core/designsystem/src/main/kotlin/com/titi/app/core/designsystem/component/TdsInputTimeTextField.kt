package com.titi.app.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import kotlinx.coroutines.android.awaitFrame

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TdsInputTimeTextField(
    modifier: Modifier = Modifier,
    hour: String,
    onHourChange: (String) -> Unit,
    minutes: String,
    onMinutesChange: (String) -> Unit,
    seconds: String,
    onSecondsChange: (String) -> Unit,
) {
    val (hourFocus, minutesFocus, secondsFocus) = FocusRequester.createRefs()
    val focusManger = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        hourFocus.requestFocus()
        awaitFrame()
        keyboard?.show()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TdsOutlinedInputTextField(
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .focusRequester(hourFocus)
                .focusProperties { next = minutesFocus },
            text = TextFieldValue(hour, selection = TextRange(hour.length)),
            onValueChange = {
                if (it.text.isDigitsOnly()) {
                    if (it.text.isBlank()) {
                        onHourChange(it.text)
                    } else {
                        if (it.text.toInt() < 24) {
                            onHourChange(it.text)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                },
            ),
            placeHolder = {
                TdsText(
                    text = "H",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 22.sp,
                    color = TdsColor.DIVIDER,
                )
            },
        )

        TdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TdsColor.TEXT,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 22.sp,
        )

        TdsOutlinedInputTextField(
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .focusRequester(minutesFocus)
                .focusProperties {
                    previous = hourFocus
                    next = secondsFocus
                },
            text = TextFieldValue(minutes, selection = TextRange(minutes.length)),
            onValueChange = {
                if (it.text.isDigitsOnly()) {
                    if (it.text.isBlank()) {
                        onMinutesChange(it.text)
                    } else {
                        if (it.text.toInt() < 60) {
                            onMinutesChange(it.text)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                },
            ),
            placeHolder = {
                TdsText(
                    text = "M",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 22.sp,
                    color = TdsColor.DIVIDER,
                )
            },
        )

        TdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TdsColor.TEXT,
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 22.sp,
        )

        TdsOutlinedInputTextField(
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .focusRequester(secondsFocus)
                .focusProperties { previous = minutesFocus },
            text = TextFieldValue(seconds, selection = TextRange(seconds.length)),
            onValueChange = {
                if (it.text.isDigitsOnly()) {
                    if (it.text.isBlank()) {
                        onSecondsChange(it.text)
                    } else {
                        if (it.text.toInt() < 60) {
                            onSecondsChange(it.text)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.clearFocus()
                },
            ),
            placeHolder = {
                TdsText(
                    text = "S",
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 22.sp,
                    color = TdsColor.DIVIDER,
                )
            },
        )
    }
}

@Preview
@Composable
private fun TdsInputTimeTextFieldPreview() {
    TiTiTheme {
        TdsInputTimeTextField(
            modifier = Modifier.width(150.dp),
            hour = "23",
            onHourChange = {},
            minutes = "55",
            onMinutesChange = {},
            seconds = "33",
            onSecondsChange = {},
        )
    }
}
