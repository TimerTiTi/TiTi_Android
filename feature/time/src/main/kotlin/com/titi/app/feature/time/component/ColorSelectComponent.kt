package com.titi.app.feature.time.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.tds.component.TtdsText
import com.titi.app.tds.theme.TtdsColor
import com.titi.app.tds.theme.TtdsTextStyle

@Composable
fun ColorSelectComponent(
    backgroundColor: Color,
    textColor: Color,
    onClickBackgroundColor: () -> Unit,
    onClickTextColor: (Boolean) -> Unit,
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
            TtdsText(
                isNoLocale = false,
                text = stringResource(R.string.recordingcolorselector_text_backgroundcolor),
                textStyle = TtdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 14.sp,
                color = TtdsColor.TEXT_MAIN,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = TtdsColor.STROKE2.getColor(),
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = backgroundColor)
                    .clickable { onClickBackgroundColor() },
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
                            },
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Color.White)
                            .clickable {
                                onClickTextColor(false)
                            },
                    )
                }
            },
        ) { balloonWindow ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TtdsText(
                    isNoLocale = false,
                    text = stringResource(R.string.recordingcolorselector_text_textcolor),
                    textStyle = TtdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TtdsColor.TEXT_MAIN,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 1.dp,
                            color = TtdsColor.STROKE2.getColor(),
                            shape = RoundedCornerShape(8.dp),
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = textColor)
                        .clickable { balloonWindow.showAlignBottom() },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ColorSelectComponentPreview() {
    TiTiTheme {
        ColorSelectComponent(
            backgroundColor = Color.Blue,
            textColor = Color.Black,
            onClickBackgroundColor = {},
            onClickTextColor = {},
        )
    }
}
