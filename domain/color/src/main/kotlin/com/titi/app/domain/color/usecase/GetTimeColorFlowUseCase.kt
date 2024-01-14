package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomain
import com.titi.app.domain.color.model.TimeColor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTimeColorFlowUseCase @Inject constructor(
    private val colorRepository: ColorRepository
) {
    operator fun invoke(): Flow<TimeColor> = colorRepository.getColorFlow()
        .map { it?.toDomain() ?: TimeColor() }
}
