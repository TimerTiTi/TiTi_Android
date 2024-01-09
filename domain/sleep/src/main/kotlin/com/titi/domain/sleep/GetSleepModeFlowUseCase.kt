package com.titi.domain.sleep

import com.titi.app.data.sleep.api.SleepRepository
import javax.inject.Inject

class GetSleepModeFlowUseCase @Inject constructor(
    private val sleepRepository: SleepRepository
) {

    operator fun invoke() = sleepRepository.getSleepFlow()

}