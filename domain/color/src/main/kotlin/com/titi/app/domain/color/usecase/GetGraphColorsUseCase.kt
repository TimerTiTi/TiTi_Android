package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import com.titi.app.domain.color.model.GraphColor
import javax.inject.Inject

class GetGraphColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke(): GraphColor? = colorRepository.getGraphColors()?.toDomainModel()
}
