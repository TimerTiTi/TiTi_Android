package com.titi.app.feature.popup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.color.ui.ColorScreen
import com.titi.app.feature.measure.ui.MeasuringScreen

class PopUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recordingMode = intent.getIntExtra(COLOR_RECORDING_MODE_KEY, 0)
        val splashResultState = intent.getStringExtra(MEASURE_SPLASH_RESULT_KEY)

        setContent {
            TiTiTheme {
                when {
                    recordingMode != 0 -> {
                        ColorScreen(recordingMode = recordingMode) {
                            finish()
                        }
                    }

                    splashResultState != null -> {
                        MeasuringScreen(splashResultState = splashResultState) {
                            val resultIntent = Intent().apply {
                                putExtra("isFinish", it)
                            }
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        }
                    }

                    else -> finish()
                }
            }
        }
    }

    companion object {
        const val COLOR_RECORDING_MODE_KEY = "colorRecordingModeKey"
        const val MEASURE_SPLASH_RESULT_KEY = "measureSplashResultKey"
    }
}
