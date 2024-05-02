package com.titi.app.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme

@Composable
fun SettingScreen() {
    val scrollState = rememberScrollState()

    Scaffold(containerColor = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TdsColor.GROUPED_BACKGROUND.getColor())
                .padding(it)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            TdsText(
                modifier = Modifier.padding(start = 16.dp),
                text = "Setting",
                textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                fontSize = 34.sp,
                color = TdsColor.TEXT,
            )

            Spacer(modifier = Modifier.height(35.dp))

            SettingServiceSection()

            Spacer(modifier = Modifier.height(35.dp))

            SettingNotificationSection()

            Spacer(modifier = Modifier.height(35.dp))

            SettingVersionSection()

            Spacer(modifier = Modifier.height(35.dp))
        }
    }
}

@Composable
private fun SettingServiceSection() {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        text = "서비스",
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    SettingRowContent(
        title = "TiTi 기능들",
        rightAreaContent = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = "",
                tint = TdsColor.LIGHT_GRAY.getColor(),
            )
        },
        onClick = {},
    )
}

@Composable
private fun SettingNotificationSection() {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        text = "알림",
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    SettingRowContent(
        title = "타이머",
        description = "종료 5분전 알림",
        rightAreaContent = {
            Switch(
                checked = true,
                onCheckedChange = {},
            )
        },
        onClick = {},
    )

    Spacer(modifier = Modifier.height(1.dp))

    SettingRowContent(
        title = "타이머",
        description = "종료 알림",
        rightAreaContent = {
            Switch(
                checked = true,
                onCheckedChange = {},
            )
        },
        onClick = {},
    )

    Spacer(modifier = Modifier.height(1.dp))

    SettingRowContent(
        title = "스톱워치",
        description = "1시간단위 경과시 알림",
        rightAreaContent = {
            Switch(
                checked = true,
                onCheckedChange = {},
            )
        },
        onClick = {},
    )
}

@Composable
private fun SettingVersionSection() {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        text = "버전 및 업데이트 내역",
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    SettingRowContent(
        title = "버전 정보",
        description = "최신버전: 7.17.1",
        rightAreaContent = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = "",
                tint = TdsColor.LIGHT_GRAY.getColor(),
            )
        },
        onClick = {},
    )

    Spacer(modifier = Modifier.height(1.dp))

    SettingRowContent(
        title = "업데이트 내역",
        rightAreaContent = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = "",
                tint = TdsColor.LIGHT_GRAY.getColor(),
            )
        },
        onClick = {},
    )
}

@Composable
private fun SettingRowContent(
    title: String,
    description: String? = null,
    rightAreaContent: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(TdsColor.SECONDARY_BACKGROUND.getColor())
            .clickable { onClick() }
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TdsText(
                text = title,
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 17.sp,
                color = TdsColor.TEXT,
            )

            Spacer(modifier = Modifier.height(4.dp))

            description?.let {
                TdsText(
                    text = it,
                    textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                    fontSize = 11.sp,
                    color = TdsColor.LIGHT_GRAY,
                )
            }
        }

        rightAreaContent()
    }
}

@PreviewLightDark
@Composable
private fun SettingScreenPreview() {
    TiTiTheme {
        SettingScreen()
    }
}
