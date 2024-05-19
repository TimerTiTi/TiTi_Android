package com.titi.app.feature.popup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.color.ui.ColorScreen

class PopUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordingMode = intent.getIntExtra(COLOR_RECORDING_MODE_KEY, 0)

        setContent {
            TiTiTheme {
                ColorScreen(
                    recordingMode = recordingMode,
                    onFinish = { finish() },
                )
            }
        }
    }

    companion object {
        const val COLOR_RECORDING_MODE_KEY = "colorRecordingModeKey"
    }
}
