package com.titi.app.feature.popup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.titi.app.core.designsystem.theme.TiTiTheme
import com.titi.app.feature.popup.navigation.PopUpNavigation

class PopUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = intent.getStringExtra(POPUP_START_DESTINATION_KEY)

        setContent {
            TiTiTheme {
                startDestination?.let {
                    PopUpNavigation(
                        startDestination = it,
                        onColorFinish = {
                            finish()
                        },
                        onMeasureFinish = {
                            finish()
                        },
                    )
                } ?: finish()
            }
        }
    }

    companion object {
        const val POPUP_START_DESTINATION_KEY = "popUpStartDestinationKey"
    }
}
