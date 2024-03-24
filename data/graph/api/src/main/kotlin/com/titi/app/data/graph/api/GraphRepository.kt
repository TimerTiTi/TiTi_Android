package com.titi.app.data.graph.api

import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import kotlinx.coroutines.flow.Flow

interface GraphRepository {

    suspend fun setGraphChecked(graphCheckedRepositoryModel: GraphCheckedRepositoryModel)

    fun getGraphCheckedFlow(): Flow<GraphCheckedRepositoryModel>

    suspend fun setGraphGoalTime(graphGoalTimeRepositoryModel: GraphGoalTimeRepositoryModel)

    fun getGraphGoalTimeFlow(): Flow<GraphGoalTimeRepositoryModel>
}
