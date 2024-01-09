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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        TdsOutlinedInputTextField(
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .focusRequester(hourFocus)
                .focusProperties { next = minutesFocus },
            text = hour,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.isBlank()) {
                        onHourChange(it)
                    } else {
                        if (it.toInt() < 24) {
                            onHourChange(it)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                }
            ),
            placeHolder = {
                TdsText(
                    text = "H",
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 22.sp,
                    color = TdsColor.dividerColor
                )
            }
        )

        TdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TdsColor.textColor,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 22.sp
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
            text = minutes,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.isBlank()) {
                        onMinutesChange(it)
                    } else {
                        if (it.toInt() < 60) {
                            onMinutesChange(it)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.moveFocus(FocusDirection.Next)
                }
            ),
            placeHolder = {
                TdsText(
                    text = "M",
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 22.sp,
                    color = TdsColor.dividerColor
                )
            }
        )

        TdsText(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = ":",
            color = TdsColor.textColor,
            textStyle = TdsTextStyle.normalTextStyle,
            fontSize = 22.sp
        )

        TdsOutlinedInputTextField(
            modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .focusRequester(secondsFocus)
                .focusProperties { previous = minutesFocus },
            text = seconds,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    if (it.isBlank()) {
                        onSecondsChange(it)
                    } else {
                        if (it.toInt() < 60) {
                            onSecondsChange(it)
                        }
                    }
                }
            },
            fontSize = 22.sp,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManger.clearFocus()
                }
            ),
            placeHolder = {
                TdsText(
                    text = "S",
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 22.sp,
                    color = TdsColor.dividerColor
                )
            }
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