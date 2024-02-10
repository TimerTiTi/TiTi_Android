package com.titi.app.domain.color.usecase

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.domain.color.mapper.toRepositoryModel
import com.titi.app.domain.color.model.GraphColor
import javax.inject.Inject

class UpdateGraphColorsUseCase @Inject constructor(
    private val colorRepository: ColorRepository,
) {
    suspend operator fun invoke(selectedIndex: Int, graphColor: GraphColor) {
        val graphColorEntries = GraphColor.GraphColorType.entries
        val updateGraphColor = if (selectedIndex == graphColor.selectedIndex) {
            GraphColor(
                selectedIndex = selectedIndex,
                direction = if (graphColor.direction == GraphColor.GraphDirection.Right) {
                    GraphColor.GraphDirection.Left
                } else {
                    GraphColor.GraphDirection.Right
                },
            )
        } else {
            GraphColor(
                selectedIndex = selectedIndex,
                direction = GraphColor.GraphDirection.Right,
                graphColors = graphColorEntries.subList(selectedIndex, graphColorEntries.size) +
                    graphColorEntries.subList(0, selectedIndex),
            )
        }

        colorRepository.setGraphColors(updateGraphColor.toRepositoryModel())
    }
}
