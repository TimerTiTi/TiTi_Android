package com.titi.feature.time.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.designsystem.R

@Composable
fun ColorSelectContent(
    backgroundColor: Color,
    textColor: Color,
    onClickBackgroundColor: () -> Unit,
    onClickTextColor : (Boolean) -> Unit,
) {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setMarginHorizontal(12)
        setCornerRadius(8f)
        setBackgroundColor(color = Color(0xCCFFFFFF))
        setBalloonAnimation(BalloonAnimation.ELASTIC)
    }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TdsText(
                text = stringResource(R.string.background),
                textStyle = TdsTextStyle.normalTextStyle,
                fontSize = 14.sp,
                color = TdsColor.textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = backgroundColor)
                    .clickable { onClickBackgroundColor() }
            )
        }

        Spacer(modifier = Modifier.width(30.dp))

        Balloon(
            builder = builder,
            balloonContent = {
                Row {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Color.Black)
                            .clickable {
                                onClickTextColor(true)
                            }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Color.White)
                            .clickable {
                                onClickTextColor(false)
                            }
                    )
                }
            }
        ) { balloonWindow ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TdsText(
                    text = stringResource(R.string.text),
                    textStyle = TdsTextStyle.normalTextStyle,
                    fontSize = 14.sp,
                    color = TdsColor.textColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = textColor)
                        .clickable { balloonWindow.showAlignBottom() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ColorSelectContentPreview() {
    TiTiTheme {
        ColorSelectContent(
            backgroundColor = Color.Blue,
            textColor = Color.Black,
            onClickBackgroundColor = {},
            onClickTextColor = {}
        )
    }
}