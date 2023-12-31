package com.titi.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.titi.core.ui.TiTiArgs.MAIN_FINISH_ARG
import com.titi.core.ui.TiTiArgs.MAIN_SPLASH_ARG
import com.titi.core.ui.TiTiArgs.MAIN_START_ARG
import com.titi.core.ui.TiTiScreens.MAIN_SCREEN
import com.titi.core.ui.TiTiScreens.SPLASH_SCREEN
import com.titi.designsystem.R

object TiTiScreens {
    const val SPLASH_SCREEN = "splash"
    const val MAIN_SCREEN = "main"
}

object TiTiArgs {
    const val MAIN_START_ARG = "startDestination"
    const val MAIN_SPLASH_ARG = "splashScreenResult"
    const val MAIN_FINISH_ARG = "isFinish"
}

object TiTiDestinations {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val MAIN_ROUTE =
        "$MAIN_SCREEN?$MAIN_START_ARG={$MAIN_START_ARG}&$MAIN_SPLASH_ARG={$MAIN_SPLASH_ARG}&$MAIN_FINISH_ARG={$MAIN_FINISH_ARG}"
}

sealed class TiTiBottomNavigationScreen(
    val route: String,
    @StringRes val stringResourceId: Int,
    @DrawableRes val drawableResourceId: Int,
) {

    data object Timer : TiTiBottomNavigationScreen("timer", R.string.timer, R.drawable.timer_icon)

    data object StopWatch :
        TiTiBottomNavigationScreen("stopwatch", R.string.stopwatch, R.drawable.stopwatch_icon)

}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.graph.id) {
        inclusive = true
    }
}

class TiTiNavigationActions(private val navController: NavController) {

    fun navigateToMain(
        startDestination: Int,
        splashScreenResult: String? = null,
        isFinish: Boolean = false
    ) {
        navController.navigate("$MAIN_SCREEN?$MAIN_START_ARG=$startDestination&$MAIN_SPLASH_ARG=$splashScreenResult&$MAIN_FINISH_ARG=$isFinish") {
            popUpToTop(navController)
        }
    }

}