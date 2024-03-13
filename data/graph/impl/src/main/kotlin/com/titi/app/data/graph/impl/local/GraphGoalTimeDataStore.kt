package com.titi.app.data.graph.impl.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.titi.app.core.util.fromJson
import com.titi.app.core.util.readFlowValue
import com.titi.app.core.util.storeValue
import com.titi.app.core.util.toJson
import com.titi.app.data.graph.impl.local.model.GraphGoalTimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GraphGoalTimeDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setGraphGoalTime(graphGoalTimeEntity: GraphGoalTimeEntity) {
        dataStore.storeValue(GRAPH_GOAL_TIME_KEY, graphGoalTimeEntity.toJson())
    }

    fun getGraphGoalTimeFlow(): Flow<GraphGoalTimeEntity?> {
        return dataStore.readFlowValue(GRAPH_GOAL_TIME_KEY).map { it?.fromJson() }
    }

    companion object {
        private const val GRAPH_GOAL_TIME_PREF_NAME = "graphGoalTimePrefName"
        private val GRAPH_GOAL_TIME_KEY = stringPreferencesKey("graphGoalTimeKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = GRAPH_GOAL_TIME_PREF_NAME,
        )
    }
}
