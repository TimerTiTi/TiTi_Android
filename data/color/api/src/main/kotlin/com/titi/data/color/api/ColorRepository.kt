package com.titi.data.color.api

import com.titi.data.color.api.model.ColorRepositoryModel
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    suspend fun setColor(colorRepositoryModel: ColorRepositoryModel)

    fun getColorFlow() : Flow<ColorRepositoryModel?>

}