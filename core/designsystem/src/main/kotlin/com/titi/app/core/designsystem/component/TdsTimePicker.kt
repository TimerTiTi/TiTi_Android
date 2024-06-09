package com.titi.app.core.designsystem.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.AMPMHours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle

@SuppressLint("DefaultLocale")
@Composable
fun TdsTimePicker(
    themeColor: TdsColor,
    pickerValue: AMPMHours,
    onValueChange: (AMPMHours) -> Unit,
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

    Balloon(
        builder = builder,
        balloonContent = {
            HoursNumberPicker(
                modifier = Modifier
                    .width(180.dp)
                    .height(120.dp),
                value = pickerValue,
                dividersColor = Color.Transparent,
                onValueChange = {
                    onValueChange(it as AMPMHours)
                },
            )
        },
    ) { balloonWindow ->
        val hourString = if (pickerValue.dayTime == AMPMHours.DayTime.AM) {
            String.format("%02d", pickerValue.hours)
        } else {
            String.format("%02d", pickerValue.hours + 12)
        }
        val minuteString = String.format("%02d", pickerValue.minutes)

        TdsText(
            modifier = Modifier
                .width(90.dp)
                .background(
                    color = themeColor
                        .getColor()
                        .copy(0.5f),
                    shape = RoundedCornerShape(4.dp),
                )
                .border(
                    width = 2.dp,
                    color = themeColor.getColor(),
                    shape = RoundedCornerShape(4.dp),
                )
                .padding(vertical = 4.dp)
                .clickable { balloonWindow.showAlignBottom() },
            text = "$hourString:$minuteString:00",
            textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
            color = TdsColor.TEXT,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )
    }
}
