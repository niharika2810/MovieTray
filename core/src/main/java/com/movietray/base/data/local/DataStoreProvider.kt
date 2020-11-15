package com.movietray.base.data.local

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * @author Niharika.Arora
 */
class DataStoreProvider(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private lateinit var dataStore: DataStore<Preferences>

    init {
        if (!::dataStore.isInitialized) {
            dataStore = applicationContext.createDataStore(name = "app_preferences")
        }
    }

    fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.catch { emit(emptyPreferences()) }
            .map { preference -> preference[key] }
    }

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {
        val KEY_NAME = preferencesKey<String>("key_user_name")
    }
}