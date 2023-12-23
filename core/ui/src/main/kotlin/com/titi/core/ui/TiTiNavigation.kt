package com.titi.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.titi.core.ui.TiTiArgs.MAIN_ARGS
import com.titi.core.ui.TiTiScreens.MAIN_SCREEN
import com.titi.core.ui.TiTiScreens.SPLASH_SCREEN
import com.titi.designsystem.R

object TiTiScreens {
    const val SPLASH_SCREEN = "splash"
    const val MAIN_SCREEN = "main"
}

object TiTiArgs {
    const val MAIN_ARGS = "splashScreenResult"
}

object TiTiDestinations {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val MAIN_ROUTE = "$MAIN_SCREEN?$MAIN_ARGS={$MAIN_ARGS}"
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

    fun navigateToMain(splashScreenResult: String) {
        navController.navigate("$MAIN_SCREEN?$MAIN_ARGS=$splashScreenResult") {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

}