package com.titi.domain.color.usecase

import com.titi.data.color.api.ColorRepository
import com.titi.domain.color.mapper.toDomain
import com.titi.domain.color.model.TimeColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetColorUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {

    operator fun invoke(): Flow<TimeColor> =
        colorRepository.getColorFlow()
            .map { it?.toDomain() ?: TimeColor() }

}