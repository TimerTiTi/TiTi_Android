package com.titi.app.data.color.impl.repository

import com.titi.app.data.color.api.ColorRepository
import com.titi.app.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.app.data.color.api.model.ColorRepositoryModel
import com.titi.app.data.color.api.model.GraphColorRepositoryModel
import com.titi.app.data.color.impl.local.ColorDataStore
import com.titi.app.data.color.impl.mapper.toLocal
import com.titi.app.data.color.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ColorRepositoryImpl @Inject constructor(
    private val colorDataStore: ColorDataStore,
) : ColorRepository {
    override suspend fun setColor(colorRepositoryModel: ColorRepositoryModel) {
        colorDataStore.setColor(colorRepositoryModel.toLocal())
    }

    override suspend fun setBackgroundColors(
        backgroundColorRepositoryModel: BackgroundColorRepositoryModel,
    ) {
        colorDataStore.setBackgroundColors(backgroundColorRepositoryModel.toLocal())
    }

    override suspend fun setGraphColors(graphColorRepositoryModel: GraphColorRepositoryModel) {
        colorDataStore.setGraphColors(graphColorRepositoryModel.toLocal())
    }

    override suspend fun getColor(): ColorRepositoryModel? {
        return colorDataStore.getColor()?.toRepositoryModel()
    }

    override suspend fun getBackgroundColors(): BackgroundColorRepositoryModel? {
        return colorDataStore.getBackgroundColors()?.toRepositoryModel()
    }

    override fun getColorFlow(): Flow<ColorRepositoryModel?> {
        return colorDataStore.getColorFlow().map { it?.toRepositoryModel() }
    }

    override fun getGraphColorsFlow(): Flow<GraphColorRepositoryModel?> {
        return colorDataStore.getGraphColorsFlow().map { it?.toRepositoryModel() }
    }
}
