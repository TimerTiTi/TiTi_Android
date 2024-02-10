package com.titi.app.feature.color.model

import com.airbnb.mvrx.MavericksState

data class ColorUiState(
    val colors: List<Long> = emptyList(),
) : MavericksState
