package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import javax.inject.Inject

class GetBackgroundColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke() = colorRepository.getBackgroundColors()?.toDomainModel()
}
