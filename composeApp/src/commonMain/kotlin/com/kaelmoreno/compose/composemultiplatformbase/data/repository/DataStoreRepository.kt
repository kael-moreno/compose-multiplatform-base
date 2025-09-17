package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
    }

    // String storage methods using DataStore Preferences
    suspend fun saveUserToken(token: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[USER_TOKEN_KEY] = token
            }
            Logger.d("DataStore: Token saved successfully", "DataStoreRepository")
            true
        } catch (e: Exception) {
            Logger.e("DataStore: Error saving token", e, "DataStoreRepository")
            false
        }
    }

    suspend fun getUserToken(): String? {
        return try {
            val preferences = dataStore.data.first()
            val token = preferences[USER_TOKEN_KEY]
            Logger.d("DataStore: Token retrieved: ${token != null}", "DataStoreRepository")
            token?.takeIf { it.isNotEmpty() }
        } catch (e: Exception) {
            Logger.e("DataStore: Error getting token", e, "DataStoreRepository")
            null
        }
    }

    suspend fun clearUserToken(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_TOKEN_KEY)
            }
            Logger.d("DataStore: Token cleared", "DataStoreRepository")
            true
        } catch (e: Exception) {
            Logger.e("DataStore: Error clearing token", e, "DataStoreRepository")
            false
        }
    }

    // Additional string storage for demo purposes
    suspend fun saveUserName(name: String): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences[USER_NAME_KEY] = name
            }
            Logger.d("DataStore: User name saved successfully", "DataStoreRepository")
            true
        } catch (e: Exception) {
            Logger.e("DataStore: Error saving user name", e, "DataStoreRepository")
            false
        }
    }

    suspend fun getUserName(): String? {
        return try {
            val preferences = dataStore.data.first()
            val name = preferences[USER_NAME_KEY]
            Logger.d("DataStore: User name retrieved: ${name != null}", "DataStoreRepository")
            name?.takeIf { it.isNotEmpty() }
        } catch (e: Exception) {
            Logger.e("DataStore: Error getting user name", e, "DataStoreRepository")
            null
        }
    }

    suspend fun clearUserName(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_NAME_KEY)
            }
            Logger.d("DataStore: User name cleared", "DataStoreRepository")
            true
        } catch (e: Exception) {
            Logger.e("DataStore: Error clearing user name", e, "DataStoreRepository")
            false
        }
    }

    // Clear all DataStore data
    suspend fun clearAllData(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            Logger.d("DataStore: All data cleared", "DataStoreRepository")
            true
        } catch (e: Exception) {
            Logger.e("DataStore: Error clearing all data", e, "DataStoreRepository")
            false
        }
    }

    // Flow-based reactive data access
    fun getUserTokenFlow(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                Logger.e("DataStore: Error in token flow", exception, "DataStoreRepository")
                emit(androidx.datastore.preferences.core.emptyPreferences())
            }
            .map { preferences ->
                preferences[USER_TOKEN_KEY]?.takeIf { it.isNotEmpty() }
            }
    }

    fun getUserNameFlow(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                Logger.e("DataStore: Error in user name flow", exception, "DataStoreRepository")
                emit(androidx.datastore.preferences.core.emptyPreferences())
            }
            .map { preferences ->
                preferences[USER_NAME_KEY]?.takeIf { it.isNotEmpty() }
            }
    }
}
