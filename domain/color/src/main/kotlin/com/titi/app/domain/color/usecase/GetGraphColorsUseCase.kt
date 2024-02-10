package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toDomainModel
import com.titi.app.domain.color.model.GraphColor
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGraphColorsUseCase @Inject constructor(
    private val graphColorRepository: ColorRepository,
) {
    operator fun invoke(): Flow<GraphColor> =
        graphColorRepository.getGraphColorsFlow().map { it?.toDomainModel() ?: GraphColor() }
}
