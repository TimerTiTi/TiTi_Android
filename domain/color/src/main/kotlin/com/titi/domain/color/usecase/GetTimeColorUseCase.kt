package com.titi.domain.color.usecase

import com.titi.data.color.api.ColorRepository
import com.titi.domain.color.mapper.toDomain
import com.titi.domain.color.model.TimeColor
import javax.inject.Inject

class GetTimeColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {

    suspend operator fun invoke() = colorRepository.getColor()?.toDomain() ?: TimeColor()

}