package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import com.titi.app.domain.color.model.GraphColor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGraphColorsFlowUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    operator fun invoke(): Flow<GraphColor?> =
        colorRepository.getGraphColorsFlow().map { it?.toDomainModel() }
}
