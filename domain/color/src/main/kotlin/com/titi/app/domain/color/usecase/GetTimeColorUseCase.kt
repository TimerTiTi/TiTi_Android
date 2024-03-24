package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import com.titi.app.domain.color.model.TimeColor
import javax.inject.Inject

class GetTimeColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke() = colorRepository.getColor()?.toDomainModel() ?: TimeColor()
}
