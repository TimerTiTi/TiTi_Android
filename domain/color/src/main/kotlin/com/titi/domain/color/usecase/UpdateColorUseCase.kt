package com.titi.domain.color.usecase

import com.titi.data.color.api.ColorRepository
import com.titi.domain.color.mapper.toRepository
import com.titi.domain.color.model.TimeColor
import javax.inject.Inject

class UpdateColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {

    suspend operator fun invoke(timeColor: TimeColor) {
        colorRepository.setColor(timeColor.toRepository())
    }

}