package com.titi.app.feature.setting.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.titi.app.core.designsystem.R
import com.titi.app.core.designsystem.component.TdsIconButton
import com.titi.app.core.designsystem.component.TdsText
import com.titi.app.core.designsystem.navigation.TdsBottomNavigationBar
import com.titi.app.core.designsystem.navigation.TopLevelDestination
import com.titi.app.core.designsystem.theme.TdsColor
import com.titi.app.core.designsystem.theme.TdsTextStyle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.ui.LanguageManager
import com.titi.app.feature.setting.model.SettingActions
import com.titi.app.feature.setting.model.SettingUiState
import com.titi.app.feature.setting.model.Version

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingScreen(
    viewModel: SettingViewModel = mavericksViewModel(),
    handleNavigateActions: (SettingActions.Navigates) -> Unit,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    val uiState by viewModel.collectAsState()

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("versions")

    val context = LocalContext.current
    val languageManager = remember { LanguageManager(context) }
    val currentLanguage by languageManager.currentLanguage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        databaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val latestVersion = snapshot
                        .children
                        .lastOrNull()
                        ?.getValue(Version::class.java)
                        ?.currentVersion

                    val currentVersion = context
                        .packageManager
                        .getPackageInfo(context.packageName, 0)
                        .versionName

                    latestVersion?.let { safeLatestVersion ->
                        viewModel.handleUpdateActions(
                            SettingActions.Updates.Version(
                                uiState.versionState.copy(
                                    newVersion = safeLatestVersion,
                                    currentVersion = currentVersion,
                                ),
                            ),
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("SettingScreen", error.message)
                }
            },
        )
    }

    val containerColor = if (isSystemInDarkTheme()) {
        0xFF000000
    } else {
        0xFFF2F2F7
    }

    Scaffold(
        containerColor = Color(containerColor),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TdsColor.GROUPED_BACKGROUND.getColor(),
                ),
                title = {
                    TdsText(
                        text = "Setting",
                        textStyle = TdsTextStyle.EXTRA_BOLD_TEXT_STYLE,
                        fontSize = 24.sp,
                        color = TdsColor.TEXT,
                    )
                },
            )
        },
        bottomBar = {
            TdsBottomNavigationBar(
                currentTopLevelDestination = TopLevelDestination.SETTING,
                bottomNavigationColor = containerColor,
                onNavigateToDestination = onNavigateToDestination,
            )
        },
    ) {
        SettingScreen(
            modifier = Modifier
                .padding(it)
                .safeDrawingPadding(),
            uiState = uiState,
            currentLanguage = currentLanguage,
            onSettingActions = { settingActions ->
                when (settingActions) {
                    is SettingActions.Navigates -> handleNavigateActions(settingActions)

                    is SettingActions.Updates -> viewModel.handleUpdateActions(settingActions)

                    is SettingActions.Language -> {
                        languageManager.setLanguage(settingActions.language)
                    }
                }
            },
        )
    }
}

@Composable
private fun SettingScreen(
    modifier: Modifier,
    uiState: SettingUiState,
    currentLanguage: String,
    onSettingActions: (SettingActions) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TdsColor.GROUPED_BACKGROUND.getColor())
            .then(modifier)
            .verticalScroll(scrollState),
    ) {
        SettingServiceSection(onSettingActions = onSettingActions)

        Spacer(modifier = Modifier.height(35.dp))

        SettingNotificationSection(
            switchState = uiState.switchState,
            onSettingActions = onSettingActions,
        )

        Spacer(modifier = Modifier.height(35.dp))

        SettingLanguageSection(
            currentLanguage = currentLanguage,
            onSettingActions = onSettingActions,
        )

        Spacer(modifier = Modifier.height(35.dp))

        SettingVersionSection(
            versionState = uiState.versionState,
            onSettingActions = onSettingActions,
        )

        Spacer(modifier = Modifier.height(35.dp))

        DeveloperSection(onSettingActions = onSettingActions)

        Spacer(modifier = Modifier.height(35.dp))
    }
}

@Composable
private fun SettingServiceSection(onSettingActions: (SettingActions) -> Unit) {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        isNoLocale = false,
        text = stringResource(R.string.settings_text_servicesection),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    ListContent(
        title = stringResource(R.string.settings_button_functions),
        rightAreaContent = {
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = "",
                tint = TdsColor.LIGHT_GRAY.getColor(),
            )
        },
        onClick = {
            onSettingActions(SettingActions.Navigates.FeaturesList)
        },
    )
}

@Composable
private fun SettingNotificationSection(
    switchState: SettingUiState.SwitchState,
    onSettingActions: (SettingActions) -> Unit,
) {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        isNoLocale = false,
        text = stringResource(R.string.setting_text_notification),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    ListContent(
        title = stringResource(R.string.common_button_timer),
        description = stringResource(R.string.switchsetting_button_5minnotidesc),
        rightAreaContent = {
            Switch(
                checked = switchState.timerFiveMinutesBeforeTheEnd,
                onCheckedChange = {
                    onSettingActions(
                        SettingActions.Updates.Switch(
                            switchState = switchState.copy(
                                timerFiveMinutesBeforeTheEnd =
                                !switchState.timerFiveMinutesBeforeTheEnd,
                            ),
                        ),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.common_button_timer),
        description = stringResource(R.string.switchsetting_button_endnotidesc),
        rightAreaContent = {
            Switch(
                checked = switchState.timerBeforeTheEnd,
                onCheckedChange = {
                    onSettingActions(
                        SettingActions.Updates.Switch(
                            switchState = switchState.copy(
                                timerBeforeTheEnd = !switchState.timerBeforeTheEnd,
                            ),
                        ),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.common_button_stopwatch),
        description = stringResource(R.string.switchsetting_button_1hourpassnotidesc),
        rightAreaContent = {
            Switch(
                checked = switchState.stopwatch,
                onCheckedChange = {
                    onSettingActions(
                        SettingActions.Updates.Switch(
                            switchState = switchState.copy(
                                stopwatch = !switchState.stopwatch,
                            ),
                        ),
                    )
                },
            )
        },
    )
}

@Composable
private fun SettingLanguageSection(
    currentLanguage: String,
    onSettingActions: (SettingActions) -> Unit,
) {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        isNoLocale = false,
        text = stringResource(R.string.setting_text_language),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    ListContent(
        title = stringResource(R.string.setting_text_system),
        rightAreaContent = {
            RadioButton(
                selected = currentLanguage == LanguageManager.SYSTEM_DEFAULT,
                onClick = {
                    onSettingActions(
                        SettingActions.Language(LanguageManager.SYSTEM_DEFAULT),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.setting_text_korean),
        rightAreaContent = {
            RadioButton(
                selected = currentLanguage == LanguageManager.KOREAN,
                onClick = {
                    onSettingActions(
                        SettingActions.Language(LanguageManager.KOREAN),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.setting_text_english),
        rightAreaContent = {
            RadioButton(
                selected = currentLanguage == LanguageManager.ENGLISH,
                onClick = {
                    onSettingActions(
                        SettingActions.Language(LanguageManager.ENGLISH),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.setting_text_china),
        rightAreaContent = {
            RadioButton(
                selected = currentLanguage == LanguageManager.CHINA,
                onClick = {
                    onSettingActions(
                        SettingActions.Language(LanguageManager.CHINA),
                    )
                },
            )
        },
    )

    Spacer(modifier = Modifier.height(1.dp))
}

@Composable
private fun SettingVersionSection(
    versionState: SettingUiState.VersionState,
    onSettingActions: (SettingActions) -> Unit,
) {
    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        isNoLocale = false,
        text = stringResource(R.string.settings_text_versionsection),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(4.dp))

    ListContent(
        title = stringResource(R.string.settings_button_versioninfotitle),
        description = stringResource(R.string.settings_text_versioninfodesc) +
            " : ${versionState.newVersion}",
        rightAreaContent = {
            TdsText(
                text = versionState.currentVersion,
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                color = TdsColor.LIGHT_GRAY,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                painter = painterResource(id = R.drawable.arrow_right_icon),
                contentDescription = "",
                tint = TdsColor.LIGHT_GRAY.getColor(),
            )
        },
        onClick = {
            onSettingActions(SettingActions.Navigates.PlayStore)
        },
    )

    Spacer(modifier = Modifier.height(1.dp))

    ListContent(
        title = stringResource(R.string.settings_button_updatehistory),
        rightAreaContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right_icon),
                    contentDescription = "",
                    tint = TdsColor.LIGHT_GRAY.getColor(),
                )
            }
        },
        onClick = {
            onSettingActions(SettingActions.Navigates.UpdatesList)
        },
    )
}

@Composable
internal fun ListContent(
    title: String,
    description: String? = null,
    rightAreaContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(TdsColor.SECONDARY_BACKGROUND.getColor())
            .clickable { onClick?.invoke() }
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TdsText(
                isNoLocale = false,
                text = title,
                textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
                fontSize = 17.sp,
                color = TdsColor.TEXT,
            )

            Spacer(modifier = Modifier.height(4.dp))

            description?.let {
                TdsText(
                    isNoLocale = false,
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

@Composable
internal fun DeveloperSection(onSettingActions: (SettingActions) -> Unit) {
    val context = LocalContext.current

    TdsText(
        modifier = Modifier.padding(start = 16.dp),
        isNoLocale = false,
        text = stringResource(R.string.setting_text_develop),
        textStyle = TdsTextStyle.SEMI_BOLD_TEXT_STYLE,
        fontSize = 14.sp,
        color = TdsColor.TEXT,
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TdsIconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(TdsColor.SECONDARY_BACKGROUND.getColor()),
            onClick = {
                onSettingActions(
                    SettingActions.Navigates.ExternalWeb("https://github.com/TimerTiTi"),
                )
            },
            size = 48.dp,
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.github_icon),
                contentDescription = null,
                tint = TdsColor.TEXT.getColor(),
            )
        }

        TdsIconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(TdsColor.SECONDARY_BACKGROUND.getColor()),
            onClick = {
                onSettingActions(
                    SettingActions.Navigates.ExternalWeb(
                        "https://www.instagram.com/study_withtiti/",
                    ),
                )
            },
            size = 48.dp,
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.instagram_icon),
                contentDescription = null,
                tint = TdsColor.TEXT.getColor(),
            )
        }

        TdsIconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(TdsColor.SECONDARY_BACKGROUND.getColor()),
            onClick = {
                val uriText = "mailto:koreatlwls@gmail.com" +
                    "?subject=" + Uri.encode("TiTi 문의사항")

                val uri = Uri.parse(uriText)

                val sendIntent = Intent(Intent.ACTION_SENDTO)
                sendIntent.data = uri

                if (sendIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(Intent.createChooser(sendIntent, "Send Email"))
                } else {
                    Toast.makeText(
                        context,
                        "이메일 앱이 존재하지 않습니다.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
            size = 48.dp,
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.email_icon),
                contentDescription = null,
                tint = TdsColor.TEXT.getColor(),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingScreenPreview() {
    TiTiTheme {
        SettingScreen(
            modifier = Modifier,
            uiState = SettingUiState(),
            currentLanguage = LanguageManager.SYSTEM_DEFAULT,
            onSettingActions = {},
        )
    }
}
