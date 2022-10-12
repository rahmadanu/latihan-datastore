package com.binar.latihan_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CounterDataStoreManager(private val context: Context) {

    suspend fun incrementCounter(context: Context) {
        context.dataStore.edit { preferences ->
            val currentCounterValue = preferences[COUNTER_KEY] ?: 0
            preferences[COUNTER_KEY] = currentCounterValue + 1
        }
    }

    suspend fun decrementCounter(context: Context) {
        context.dataStore.edit { preferences ->
            val currentCounterValue = preferences[COUNTER_KEY] ?: 0
            preferences[COUNTER_KEY] = currentCounterValue - 1
        }
    }

    suspend fun getPreferences(context: Context): Flow<Preferences> {
        return context.dataStore.data.catch { exception ->
            exception.printStackTrace()
        }
    }

    suspend fun setCounter(counterValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER_KEY] = counterValue
        }
    }

    @JvmName("getCounter1")
    fun getCounter(): Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[COUNTER_KEY] ?: 0
        }
    }

    val counter: Flow<Int>
        get() = context.dataStore.data.map { preferences ->
            preferences[COUNTER_KEY] ?: 0
        }

    companion object {
        private const val DATA_STORE_NAME = "counter_preferences"
        private val COUNTER_KEY = intPreferencesKey("counter_key")

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }
}