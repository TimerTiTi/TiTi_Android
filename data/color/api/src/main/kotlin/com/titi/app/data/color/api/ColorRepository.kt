package com.titi.app.data.color.api

import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.api.model.GraphColorRepositoryModel
import kotlinx.coroutines.flow.Flow

interface ColorRepository {
    suspend fun setColor(colorRepositoryModel: ColorRepositoryModel)

    suspend fun setBackgroundColors(backgroundColorRepositoryModel: BackgroundColorRepositoryModel)

    suspend fun setGraphColors(graphColorRepositoryModel: GraphColorRepositoryModel)

    suspend fun getColor(): ColorRepositoryModel?

    suspend fun getBackgroundColors(): BackgroundColorRepositoryModel?

    fun getColorFlow(): Flow<ColorRepositoryModel?>

    fun getGraphColorsFlow(): Flow<GraphColorRepositoryModel?>
}
