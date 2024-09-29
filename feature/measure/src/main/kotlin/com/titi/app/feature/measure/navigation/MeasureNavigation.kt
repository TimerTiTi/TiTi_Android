package com.titi.app.feature.measure.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.titi.app.feature.measure.ui.MeasuringScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

private const val MEASURE_SCREEN = "measure"
const val MEASURE_ARG = "splashResultState"
const val MEASURE_ROUTE = "$MEASURE_SCREEN?$MEASURE_ARG={$MEASURE_ARG}"

fun NavController.navigateToMeasure(splashResultState: String) {
    navigate("$MEASURE_SCREEN?$MEASURE_ARG=$splashResultState")
}

fun NavGraphBuilder.measureGraph(onFinish: (isFinish: Boolean) -> Unit) {
    composable(
        route = MEASURE_ROUTE,
        arguments = listOf(
            navArgument(MEASURE_ARG) {
                type = NavType.StringType
            },
        ),
    ) {
        MeasuringScreen(
            splashResultState = it.arguments
                ?.getString(MEASURE_ARG, "")
                ?.let { jsonString ->
                    URLDecoder.decode(
                        jsonString,
                        StandardCharsets.UTF_8.toString(),
                    )
                }
                ?: "",
            onFinish = onFinish,
        )
    }
}
