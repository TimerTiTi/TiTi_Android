package com.titi.feature.main.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.titi.feature.main.model.SplashResultState

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onReady : (SplashResultState) -> Unit,
){
    LaunchedEffect(Unit) {
        viewModel.getSplashResultState()
    }

    val splashResultState by viewModel.splashResultState.collectAsStateWithLifecycle()

    LaunchedEffect(splashResultState){
        splashResultState?.let{
            onReady(it)
        }
    }
}