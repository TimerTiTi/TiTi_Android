package com.titi.app.feature.log.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsColorRow
import com.titi.app.core.designsystem.component.TdsTabRow
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun SettingBottomSheet() {
}

@Composable
private fun SettingBottomSheetContent() {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val width = if (maxWidth >= 365.dp) 345.dp else maxWidth - 20.dp

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.log_setting_color_title),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.log_setting_color_message),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )
            }

            TdsColorRow(
                modifier = Modifier.fillMaxWidth(),
                onClick = { },
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.log_setting_color_direction_title),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.log_setting_color_direction_message),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TdsTabRow(
                        modifier = Modifier
                            .width(60.dp)
                            .height(24.dp),
                        selectedItemIndex = 0,
                        items = listOf(
                            "→",
                            "←",
                        ),
                        onClick = {},
                    )

                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        TdsColor.D1.getColor(),
                                        TdsColor.D2.getColor(),
                                    ),
                                ),
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .width(width)
                    .padding(vertical = 10.dp),
            ) {
                TdsText(
                    text = stringResource(id = R.string.log_setting_goal_time_title),
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 16.sp,
                    color = TdsColor.TEXT,
                )

                Spacer(modifier = Modifier.height(4.dp))

                TdsText(
                    text = stringResource(id = R.string.log_setting_goal_time_message),
                    textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                    fontSize = 14.sp,
                    color = TdsColor.LIGHT_GRAY,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = TdsColor.SEGMENT_BACKGROUND.getColor())
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Month",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 16.sp,
                        color = TdsColor.TEXT,
                    )

                    TdsText(
                        text = "100 H",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 14.sp,
                        color = TdsColor.TEXT,
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = TdsColor.SEGMENT_BACKGROUND.getColor())
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TdsText(
                        text = "Week",
                        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                        fontSize = 16.sp,
                        color = TdsColor.TEXT,
                    )

                    TdsText(
                        text = "100 H",
                        textStyle = TdsTextStyle.NORMAL_TEXT_STYLE,
                        fontSize = 14.sp,
                        color = TdsColor.TEXT,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
private fun SettingBottomSheetContentPreview() {
    TiTiTheme {
        SettingBottomSheetContent()
    }
}
