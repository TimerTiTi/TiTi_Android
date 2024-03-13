package com.titi.app.data.graph.api

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import kotlinx.coroutines.flow.Flow

interface GraphCheckedRepository {

    suspend fun setGraphChecked(graphCheckedRepositoryModel: GraphCheckedRepositoryModel)

    fun getGraphCheckedFlow(): Flow<GraphCheckedRepositoryModel>
}
