package com.titi.app.data.graph.impl.repository

import com.titi.app.data.graph.api.GraphRepository
import com.titi.app.data.graph.api.model.GraphCheckedRepositoryModel
import com.titi.app.data.graph.api.model.GraphGoalTimeRepositoryModel
import com.titi.app.data.graph.impl.local.GraphCheckedDataStore
import com.titi.app.data.graph.impl.local.GraphGoalTimeDataStore
import com.titi.app.data.graph.impl.mapper.toLocalModel
import com.titi.app.data.graph.impl.mapper.toRepositoryModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

internal class GraphRepositoryImpl @Inject constructor(
    private val graphCheckedDataStore: GraphCheckedDataStore,
    private val graphGoalTimeDataStore: GraphGoalTimeDataStore,
) : GraphRepository {
    override suspend fun setGraphChecked(graphCheckedRepositoryModel: GraphCheckedRepositoryModel) {
        graphCheckedDataStore.setGraphChecked(graphCheckedRepositoryModel.toLocalModel())
    }

    override fun getGraphCheckedFlow(): Flow<GraphCheckedRepositoryModel> {
        return graphCheckedDataStore
            .getGraphCheckedFlow()
            .filterNotNull()
            .map { it.toRepositoryModel() }
    }

    override suspend fun setGraphGoalTime(
        graphGoalTimeRepositoryModel: GraphGoalTimeRepositoryModel,
    ) {
        graphGoalTimeDataStore.setGraphGoalTime(graphGoalTimeRepositoryModel.toLocalModel())
    }

    override fun getGraphGoalTimeFlow(): Flow<GraphGoalTimeRepositoryModel> {
        return graphGoalTimeDataStore
            .getGraphGoalTimeFlow()
            .filterNotNull()
            .map { it.toRepositoryModel() }
    }
}
