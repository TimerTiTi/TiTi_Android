package com.titi.app.feature.main.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.core.ui.removeNotification
import com.titi.app.feature.main.navigation.TiTiApp
import com.titi.app.tds.theme.TtdsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            viewModel.splashResultState == null
        }

        enableEdgeToEdge()

        setContent {
            TiTiTheme {
                TtdsTheme {
                    viewModel.splashResultState?.let {
                        TiTiApp(
                            splashResultState = it,
                            isConfigurationChange = savedInstanceState != null,
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        this.removeNotification()
    }
}
