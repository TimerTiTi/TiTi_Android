package com.titi.data.color.impl.repository

import com.titi.data.color.api.ColorRepository
import com.titi.data.color.api.model.ColorRepositoryModel
import com.titi.data.color.impl.local.ColorDataStore
import com.titi.data.color.impl.mapper.toLocal
import com.titi.data.color.impl.mapper.toRepositoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ColorRepositoryImpl @Inject constructor(
    private val colorDataStore: ColorDataStore
) : ColorRepository {

    override suspend fun setColor(colorRepositoryModel: ColorRepositoryModel) {
        colorDataStore.setColor(colorRepositoryModel.toLocal())
    }

    override fun getColorFlow(): Flow<ColorRepositoryModel?> {
        return colorDataStore.getColorFlow().map { it?.toRepositoryModel() }
    }

}