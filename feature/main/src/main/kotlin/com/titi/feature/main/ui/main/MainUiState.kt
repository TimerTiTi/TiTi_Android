package com.titi.feature.main.ui.main

import com.airbnb.mvrx.MavericksState
import com.titi.app.domain.color.model.TimeColor

data class MainUiState(
    val timeColor: TimeColor = TimeColor(),
    val bottomNavigationPosition: Int = 0,
) : MavericksState