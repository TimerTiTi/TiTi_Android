package com.titi.app.tds.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle
import com.titi.app.tds.theme.TtdsTheme
import kotlinx.coroutines.android.awaitFrame

@Composable
fun TtdsInputTimeTextField(
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
        horizontalArrangement = Arrangement.Center,
    ) {
        TtdsOutLinedTextField(
            modifier = Modifier
                .width(65.dp)
                .focusRequester(hourFocus)
                .focusProperties { next = minutesFocus },
            text = TextFieldValue(hour, selection = TextRange(hour.length)),
            textAlign = TextAlign.Center,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                },
            ),
            fontSize = 20.sp,
            placeholder = "H",
        )

        TtdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TtdsColor.TEXT_MAIN,
            textStyle = TtdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 22.sp,
        )

        TtdsOutLinedTextField(
            modifier = Modifier
                .width(65.dp)
                .focusRequester(minutesFocus)
                .focusProperties {
                    previous = hourFocus
                    next = secondsFocus
                },
            text = TextFieldValue(minutes, selection = TextRange(minutes.length)),
            textAlign = TextAlign.Center,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                },
            ),
            fontSize = 20.sp,
            placeholder = "M",
        )

        TtdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TtdsColor.TEXT_MAIN,
            textStyle = TtdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            fontSize = 22.sp,
        )

        TtdsOutLinedTextField(
            modifier = Modifier
                .width(65.dp)
                .focusRequester(secondsFocus)
                .focusProperties { previous = minutesFocus },
            text = TextFieldValue(seconds, selection = TextRange(seconds.length)),
            textAlign = TextAlign.Center,
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.clearFocus()
                },
            ),
            placeholder = "S",
            fontSize = 20.sp,
        )
    }
}

@Preview
@Composable
private fun TtdsInputTimeTextFieldPreview() {
    TtdsTheme {
        TtdsInputTimeTextField(
            modifier = Modifier.fillMaxWidth(),
            hour = "",
            onHourChange = {},
            minutes = "55",
            onMinutesChange = {},
            seconds = "33",
            onSecondsChange = {},
        )
    }
}
