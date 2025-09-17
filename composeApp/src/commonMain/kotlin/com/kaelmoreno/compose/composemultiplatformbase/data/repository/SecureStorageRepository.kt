package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.UserPreferences
import eu.anifantakis.lib.ksafe.KSafe

class SecureStorageRepository(private val kSafe: KSafe) {

    companion object {
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_USER_PREFERENCES = "user_preferences"
    }

    // String storage methods using suspend API
    suspend fun saveUserToken(token: String): Boolean {
        return try {
            kSafe.put(KEY_USER_TOKEN, token, true)
            true
        } catch (e: Exception) {
            Logger.e("Error saving user token", e)
            false
        }
    }

    suspend fun getUserToken(): String? {
        return try {
            kSafe.get(KEY_USER_TOKEN, "").takeIf { it.isNotEmpty() }
        } catch (e: Exception) {
            Logger.e("Error getting token", e)
            null
        }
    }

    suspend fun clearUserToken(): Boolean {
        return try {
            kSafe.delete(KEY_USER_TOKEN)
            true
        } catch (e: Exception) {
            false
        }
    }

    // JSON storage methods using suspend API
    suspend fun saveUserPreferences(preferences: UserPreferences): Boolean {
        return try {
            kSafe.put(KEY_USER_PREFERENCES, preferences, true)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserPreferences(): UserPreferences? {
        return try {
            // Use a default UserPreferences object and check if it's different from default
            val defaultPrefs = UserPreferences("", "", false, "")
            val result = kSafe.get(KEY_USER_PREFERENCES, defaultPrefs)
            if (result == defaultPrefs && result.username.isEmpty()) null else result
        } catch (e: Exception) {
            null
        }
    }

    suspend fun clearUserPreferences(): Boolean {
        return try {
            kSafe.delete(KEY_USER_PREFERENCES)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Clear all data by deleting individual keys
    suspend fun clearAllData(): Boolean {
        return try {
            kSafe.delete(KEY_USER_TOKEN)
            kSafe.delete(KEY_USER_PREFERENCES)
            true
        } catch (e: Exception) {
            false
        }
    }
}
