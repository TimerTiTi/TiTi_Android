package com.titi.data.color.api

import com.titi.data.color.api.model.BackgroundColorRepositoryModel
import com.titi.data.color.api.model.ColorRepositoryModel
import kotlinx.coroutines.flow.Flow

interface ColorRepository {

    suspend fun setColor(colorRepositoryModel: ColorRepositoryModel)

    suspend fun setBackgroundColors(backgroundColorRepositoryModel: BackgroundColorRepositoryModel)

    suspend fun getColor(): ColorRepositoryModel?

    suspend fun getBackgroundColors(): BackgroundColorRepositoryModel?

    fun getColorFlow(): Flow<ColorRepositoryModel?>

}