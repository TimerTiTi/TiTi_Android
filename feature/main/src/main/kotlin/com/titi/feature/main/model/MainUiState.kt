package com.titi.feature.main.model

import com.airbnb.mvrx.MavericksState
import com.titi.domain.color.model.TimeColor
import com.titi.domain.time.model.RecordTimes

data class MainUiState(
    val recordTimes: RecordTimes = RecordTimes(),
    val timeColor: TimeColor = TimeColor(),
    val bottomNavigationPosition: Int = 0,
) : MavericksState