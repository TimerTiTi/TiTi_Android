package com.titi.feature.time

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.titi.core.designsystem.theme.TiTiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ColorActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TiTiTheme {

            }
        }
    }

}

@Composable
fun ColorScreen(

){

}