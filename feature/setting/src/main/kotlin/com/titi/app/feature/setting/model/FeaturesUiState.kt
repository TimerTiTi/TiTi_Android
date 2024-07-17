package com.titi.app.feature.setting.model

import com.airbnb.mvrx.MavericksState

data class FeaturesUiState(
    val features: List<Feature> = makeFeatures(),
) : MavericksState {
    data class Feature(
        val title: String,
        val url: String,
    )
}

internal fun makeFeatures(): List<FeaturesUiState.Feature> {
    return listOf(
        FeaturesUiState.Feature(
            title = "새로운 기록 설정",
            url = "https://www.notion.so/timertiti/2501881bb0ef49c29a1c2cee29b7f48e?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Task",
            url = "https://www.notion.so/timertiti/Task-5fbd947fe3994ce09dd3d87051861005?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Timer",
            url = "https://www.notion.so/timertiti/Timer-0083c63a3a464fc69b6c255930690ae8?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Stopwatch",
            url =
            "https://www.notion.so/timertiti/Stopwatch-41984a8ab11444cba79fb94984f799bb?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Log",
            url = "https://www.notion.so/timertiti/Log-362d4cffb3e74f1686dd4e603fba8496?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Daily",
            url = "https://www.notion.so/timertiti/Daily-d60dc90f3c104744a74985ea221e5691?pvs=4",
        ),
        FeaturesUiState.Feature(
            title = "Daily 수정/생성",
            url = "https://timertiti.notion.site/Daily-f3b7898bcda541dda3ac526ea6a56313?pvs=4",
        ),
    )
}
