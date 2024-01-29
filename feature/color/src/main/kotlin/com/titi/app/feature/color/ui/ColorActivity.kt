package com.titi.app.feature.color.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titi.app.core.designsystem.theme.TiTiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordingMode = intent.getIntExtra(
            "recordingMode",
            1,
        )

        setContent {
            TiTiTheme {
                ColorScreen(
                    recordingMode = recordingMode,
                    onFinish = {
                        finish()
                    },
                )
            }
        }
    }
}
