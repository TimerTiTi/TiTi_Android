package com.titi.domain.color.usecase

import com.titi.data.color.api.ColorRepository
import com.titi.domain.color.mapper.toDomain
import com.titi.domain.color.mapper.toRepository
import com.titi.domain.color.model.TimeColor
import javax.inject.Inject

class UpdateColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {

    suspend operator fun invoke(
        recordingMode: Int,
        backgroundColor: Long? = null,
        isTextColorBlack: Boolean = false,
    ) {
        val timeColor = colorRepository.getColor()?.toDomain() ?: TimeColor()
        val updateTimeColor = if (recordingMode == 1) {
            if (backgroundColor != null) {
                timeColor.copy(
                    timerBackgroundColor = backgroundColor,
                    isTimerBlackTextColor = isTextColorBlack,
                )
            } else {
                timeColor.copy(
                    isTimerBlackTextColor = isTextColorBlack
                )
            }
        } else {
            if (backgroundColor != null) {
                timeColor.copy(
                    timerBackgroundColor = backgroundColor,
                    isTimerBlackTextColor = isTextColorBlack,
                )
            } else {
                timeColor.copy(
                    isTimerBlackTextColor = isTextColorBlack
                )
            }
        }
        colorRepository.setColor(updateTimeColor.toRepository())
    }

}