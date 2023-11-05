package com.titi.domain.color.usecase

import com.titi.data.color.api.ColorRepository
import com.titi.domain.color.mapper.toDomain
import javax.inject.Inject

class GetBackgroundColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {

    suspend operator fun invoke() =
        colorRepository.getBackgroundColors()?.toDomain()

}