package com.titi.app.feature.main.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titi.app.doamin.daily.usecase.GetCurrentDailyFlowUseCase
import com.titi.app.domain.color.usecase.GetTimeColorFlowUseCase
import com.titi.app.domain.time.usecase.GetRecordTimesFlowUseCase
import com.titi.app.feature.main.model.SplashResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn

@HiltViewModel
class MainViewModel @Inject constructor(
    getRecordTimesFlowUseCase: GetRecordTimesFlowUseCase,
    getTimeColorFlowUseCase: GetTimeColorFlowUseCase,
    getCurrentDailyFlowUseCase: GetCurrentDailyFlowUseCase,
) : ViewModel() {

    val splashResultState: SharedFlow<SplashResultState?> = combine(
        getRecordTimesFlowUseCase(),
        getTimeColorFlowUseCase(),
        getCurrentDailyFlowUseCase(),
    ) { recordTimes, timeColor, daily ->
        SplashResultState(
            recordTimes = recordTimes,
            timeColor = timeColor,
            daily = daily,
        )
    }.shareIn(
        started = SharingStarted.WhileSubscribed(),
        scope = viewModelScope,
        replay = 0,
    )
}
