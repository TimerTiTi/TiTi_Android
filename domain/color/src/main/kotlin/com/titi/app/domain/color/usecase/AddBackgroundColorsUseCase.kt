package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toRepositoryModel
import com.titi.app.domain.color.model.BackgroundColors
import javax.inject.Inject

class AddBackgroundColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke(colors: List<Long>, color: Long) {
        val newColors =
            if (colors.size < 11) {
                colors.toMutableList()
                    .apply { add(color) }
                    .toList()
            } else {
                colors.toMutableList()
                    .apply {
                        removeAt(0)
                        add(color)
                    }.toList()
            }

        colorRepository.setBackgroundColors(BackgroundColors(newColors).toRepositoryModel())
    }
}
