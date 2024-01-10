package com.titi.app.feature.main.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.main.ui.TiTiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getTimeColorFlowUseCase: GetTimeColorFlowUseCase

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        setContent {
            TiTiTheme {
                TiTiApp(
                    windowSizeClass = calculateWindowSizeClass(this),
                    getTimeColorFlowUseCase = getTimeColorFlowUseCase,
                    onReady = {
                        splashScreen.setKeepOnScreenCondition { false }
                    }
                )
            }
        }
    }
}
