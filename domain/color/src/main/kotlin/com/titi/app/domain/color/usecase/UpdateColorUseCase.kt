package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import com.titi.app.domain.color.mapper.toRepositoryModel
import com.titi.app.domain.color.model.TimeColor
import javax.inject.Inject

class UpdateColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke(
        recordingMode: Int,
        backgroundColor: Long? = null,
        isTextColorBlack: Boolean = false,
    ) {
        val timeColor = colorRepository.getColor()?.toDomainModel() ?: TimeColor()
        val updateTimeColor =
            if (recordingMode == 1) {
                if (backgroundColor != null) {
                    timeColor.copy(
                        timerBackgroundColor = backgroundColor,
                        isTimerBlackTextColor = isTextColorBlack,
                    )
                } else {
                    timeColor.copy(
                        isTimerBlackTextColor = isTextColorBlack,
                    )
                }
            } else {
                if (backgroundColor != null) {
                    timeColor.copy(
                        stopwatchBackgroundColor = backgroundColor,
                        isStopwatchBlackTextColor = isTextColorBlack,
                    )
                } else {
                    timeColor.copy(
                        isStopwatchBlackTextColor = isTextColorBlack,
                    )
                }
            }
        colorRepository.setColor(updateTimeColor.toRepositoryModel())
    }
}
