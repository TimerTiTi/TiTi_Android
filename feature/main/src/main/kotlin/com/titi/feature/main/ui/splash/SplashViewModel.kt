package com.titi.feature.main.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titi.app.doamin.daily.usecase.GetCurrentDailyUseCase
import com.titi.app.domain.color.usecase.GetTimeColorUseCase
import com.titi.domain.time.usecase.GetRecordTimesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getRecordTimesUseCase: GetRecordTimesUseCase,
    private val getTimeColorUseCase: GetTimeColorUseCase,
    private val getCurrentDailyUseCase : GetCurrentDailyUseCase,
) : ViewModel() {

    private val _splashResultState: MutableStateFlow<SplashResultState?> = MutableStateFlow(null)
    val splashResultState = _splashResultState.asStateFlow()

    fun getSplashResultState() {
        viewModelScope.launch {
            val recordTimesResult = async {
                getRecordTimesUseCase()
            }

            val timeColorResult = async {
                getTimeColorUseCase()
            }

            val dailyResult = async {
                getCurrentDailyUseCase()
            }

            _splashResultState.value = SplashResultState(
                recordTimes = recordTimesResult.await(),
                timeColor = timeColorResult.await(),
                daily = dailyResult.await()
            )
        }
    }
}