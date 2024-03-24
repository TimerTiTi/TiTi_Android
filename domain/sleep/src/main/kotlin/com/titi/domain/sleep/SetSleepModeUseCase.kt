package com.titi.domain.sleep

import com.titi.app.data.sleep.api.SleepRepository
import javax.inject.Inject

class SetSleepModeUseCase @Inject constructor(
    private val sleepRepository: SleepRepository,
) {
    suspend operator fun invoke(isSleep: Boolean) {
        sleepRepository.setSleep(isSleep)
    }
}
