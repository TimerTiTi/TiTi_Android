package com.titi.app.feature.edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.titi.app.feature.edit.ui.EditScreen
import java.time.LocalDate

private const val EDIT_SCREEN = "edit"
private const val EDIT_CURRENT_DATE_ARG = "currentDate"
private const val EDIT_ROUTE = "$EDIT_SCREEN=$EDIT_CURRENT_DATE_ARG={$EDIT_CURRENT_DATE_ARG}"

fun NavController.navigateToEdit(currentDate: String) {
    navigate("$EDIT_SCREEN=$EDIT_CURRENT_DATE_ARG=$currentDate")
}

fun NavGraphBuilder.editGraph(onBack: () -> Unit) {
    composable(route = EDIT_ROUTE) {
        EditScreen(
            currentDate = LocalDate.parse(it.arguments?.getString(EDIT_CURRENT_DATE_ARG)),
            onBack = onBack,
        )
    }
}
