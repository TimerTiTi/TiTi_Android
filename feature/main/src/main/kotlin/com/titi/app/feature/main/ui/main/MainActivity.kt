package com.titi.app.feature.main.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.feature.main.ui.SplashResultState
import com.titi.app.feature.main.ui.TiTiApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var getTimeColorFlowUseCase: GetTimeColorFlowUseCase

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var splashResultState: SplashResultState? by mutableStateOf(null)

        val splashScreen = installSplashScreen()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashResultState = viewModel.splashResultState.filterNotNull().first()
            }
        }
        splashScreen.setKeepOnScreenCondition {
            splashResultState == null
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),
        )

        setContent {
            TiTiTheme {
                splashResultState?.let {
                    TiTiApp(
                        splashResultState = it,
                        windowSizeClass = calculateWindowSizeClass(this),
                        getTimeColorFlowUseCase = getTimeColorFlowUseCase,
                    )
                }
            }
        }
    }
}
