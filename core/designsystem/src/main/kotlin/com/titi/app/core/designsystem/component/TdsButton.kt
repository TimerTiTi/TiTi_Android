package com.titi.app.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun TdsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TdsTextStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
    textColor: TdsColor,
    fontSize: TextUnit,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .widthIn(min = 36.dp)
            .heightIn(min = 36.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(12.dp, 8.dp),
    ) {
        TdsText(
            text = text,
            textStyle = textStyle,
            color = textColor,
            fontSize = fontSize,
        )
    }
}

@Composable
fun TdsTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TdsTextStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
    textColor: Color,
    fontSize: TextUnit,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .widthIn(min = 36.dp)
            .heightIn(min = 36.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(12.dp, 8.dp),
    ) {
        TdsText(
            text = text,
            textStyle = textStyle,
            color = textColor,
            fontSize = fontSize,
        )
    }
}

@Composable
fun TdsIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    size: Dp = 32.dp,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = size / 2,
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Preview
@Composable
private fun TdsButtonPreview() {
    TiTiTheme {
        Column {
            TdsTextButton(
                text = "ABC",
                fontSize = 16.sp,
                onClick = { },
                textColor = TdsColor.TEXT,
            )
        }
    }
}
