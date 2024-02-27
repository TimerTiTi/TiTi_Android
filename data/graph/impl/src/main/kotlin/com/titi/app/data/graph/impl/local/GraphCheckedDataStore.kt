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
import com.titi.app.data.graph.impl.local.model.GraphCheckedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GraphCheckedDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun setGraphChecked(graphCheckedEntity: GraphCheckedEntity) {
        dataStore.storeValue(GRAPH_CHECKED_KEY, graphCheckedEntity.toJson())
    }

    fun getGraphCheckedFlow(): Flow<GraphCheckedEntity?> {
        return dataStore.readFlowValue(GRAPH_CHECKED_KEY).map { it?.fromJson() }
    }

    companion object {
        private const val GRAPH_CHECKED_PREF_NAME = "graphCheckedPrefName"
        private val GRAPH_CHECKED_KEY = stringPreferencesKey("graphCheckedKey")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = GRAPH_CHECKED_PREF_NAME,
        )
    }
}
