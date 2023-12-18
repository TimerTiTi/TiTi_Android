package com.titi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.titi.domain.color.usecase.GetTimeColorUseCase
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
    private val getTimeColorUseCase: GetTimeColorUseCase
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

            _splashResultState.value = SplashResultState(
                recordTimes = recordTimesResult.await(),
                timeColor = timeColorResult.await()
            )
        }
    }
}