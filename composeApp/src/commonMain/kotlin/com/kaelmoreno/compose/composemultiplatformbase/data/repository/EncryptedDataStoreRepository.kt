package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.encryption.EncryptionService
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class EncryptedDataStoreRepository(
    private val dataStore: DataStore<Preferences>,
    private val encryptionService: EncryptionService
) {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_encrypted")
        private val USER_NAME_KEY = stringPreferencesKey("user_name_encrypted")
        private val USER_JSON_KEY = stringPreferencesKey("user_json_encrypted")
        private const val TAG = "EncryptedDataStoreRepository"
    }

    // String storage methods with encryption
    suspend fun saveUserToken(token: String): Boolean {
        return try {
            val encryptedToken = encryptionService.encrypt(token)
            if (encryptedToken != null) {
                dataStore.edit { preferences ->
                    preferences[USER_TOKEN_KEY] = encryptedToken
                }
                Logger.d("Encrypted token saved successfully", TAG)
                true
            } else {
                Logger.e("Failed to encrypt token", null, TAG)
                false
            }
        } catch (e: Exception) {
            Logger.e("Error saving encrypted token", e, TAG)
            false
        }
    }

    suspend fun getUserToken(): String? {
        return try {
            val preferences = dataStore.data.first()
            val encryptedToken = preferences[USER_TOKEN_KEY]
            if (encryptedToken != null) {
                val decryptedToken = encryptionService.decrypt(encryptedToken)
                Logger.d("Token retrieved and decrypted: ${decryptedToken != null}", TAG)
                decryptedToken?.takeIf { it.isNotEmpty() }
            } else {
                Logger.d("No encrypted token found", TAG)
                null
            }
        } catch (e: Exception) {
            Logger.e("Error getting encrypted token", e, TAG)
            null
        }
    }

    suspend fun clearUserToken(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_TOKEN_KEY)
            }
            Logger.d("Encrypted token cleared", TAG)
            true
        } catch (e: Exception) {
            Logger.e("Error clearing encrypted token", e, TAG)
            false
        }
    }

    // Additional string storage for demo purposes
    suspend fun saveUserName(name: String): Boolean {
        return try {
            val encryptedName = encryptionService.encrypt(name)
            if (encryptedName != null) {
                dataStore.edit { preferences ->
                    preferences[USER_NAME_KEY] = encryptedName
                }
                Logger.d("Encrypted user name saved successfully", TAG)
                true
            } else {
                Logger.e("Failed to encrypt user name", null, TAG)
                false
            }
        } catch (e: Exception) {
            Logger.e("Error saving encrypted user name", e, TAG)
            false
        }
    }

    suspend fun getUserName(): String? {
        return try {
            val preferences = dataStore.data.first()
            val encryptedName = preferences[USER_NAME_KEY]
            if (encryptedName != null) {
                val decryptedName = encryptionService.decrypt(encryptedName)
                Logger.d("User name retrieved and decrypted: ${decryptedName != null}", TAG)
                decryptedName?.takeIf { it.isNotEmpty() }
            } else {
                Logger.d("No encrypted user name found", TAG)
                null
            }
        } catch (e: Exception) {
            Logger.e("Error getting encrypted user name", e, TAG)
            null
        }
    }

    suspend fun clearUserName(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_NAME_KEY)
            }
            Logger.d("Encrypted user name cleared", TAG)
            true
        } catch (e: Exception) {
            Logger.e("Error clearing encrypted user name", e, TAG)
            false
        }
    }

    // JSON storage methods for User object with encryption
    suspend fun saveUser(user: User): Boolean {
        return try {
            val jsonString = json.encodeToString(user)
            val encryptedJson = encryptionService.encrypt(jsonString)
            if (encryptedJson != null) {
                dataStore.edit { preferences ->
                    preferences[USER_JSON_KEY] = encryptedJson
                }
                Logger.d("Encrypted user JSON saved successfully", TAG)
                true
            } else {
                Logger.e("Failed to encrypt user JSON", null, TAG)
                false
            }
        } catch (e: Exception) {
            Logger.e("Error saving encrypted user JSON", e, TAG)
            false
        }
    }

    suspend fun getUser(): User? {
        return try {
            val preferences = dataStore.data.first()
            val encryptedJson = preferences[USER_JSON_KEY]
            if (encryptedJson != null) {
                val decryptedJson = encryptionService.decrypt(encryptedJson)
                if (decryptedJson != null) {
                    val user = json.decodeFromString<User>(decryptedJson)
                    Logger.d("User JSON retrieved and decrypted successfully", TAG)
                    user
                } else {
                    Logger.e("Failed to decrypt user JSON", null, TAG)
                    null
                }
            } else {
                Logger.d("No encrypted user JSON found", TAG)
                null
            }
        } catch (e: Exception) {
            Logger.e("Error getting encrypted user JSON", e, TAG)
            null
        }
    }

    suspend fun clearUser(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(USER_JSON_KEY)
            }
            Logger.d("Encrypted user JSON cleared", TAG)
            true
        } catch (e: Exception) {
            Logger.e("Error clearing encrypted user JSON", e, TAG)
            false
        }
    }

    // Clear all encrypted DataStore data
    suspend fun clearAllData(): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            Logger.d("All encrypted data cleared", TAG)
            true
        } catch (e: Exception) {
            Logger.e("Error clearing all encrypted data", e, TAG)
            false
        }
    }

    // Flow-based reactive data access with decryption
    fun getUserTokenFlow(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                Logger.e("Error in encrypted token flow", exception, TAG)
                emit(androidx.datastore.preferences.core.emptyPreferences())
            }
            .map { preferences ->
                try {
                    val encryptedToken = preferences[USER_TOKEN_KEY]
                    if (encryptedToken != null) {
                        encryptionService.decrypt(encryptedToken)?.takeIf { it.isNotEmpty() }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Logger.e("Error decrypting token in flow", e, TAG)
                    null
                }
            }
    }

    fun getUserNameFlow(): Flow<String?> {
        return dataStore.data
            .catch { exception ->
                Logger.e("Error in encrypted user name flow", exception, TAG)
                emit(androidx.datastore.preferences.core.emptyPreferences())
            }
            .map { preferences ->
                try {
                    val encryptedName = preferences[USER_NAME_KEY]
                    if (encryptedName != null) {
                        encryptionService.decrypt(encryptedName)?.takeIf { it.isNotEmpty() }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Logger.e("Error decrypting user name in flow", e, TAG)
                    null
                }
            }
    }

    fun getUserFlow(): Flow<User?> {
        return dataStore.data
            .catch { exception ->
                Logger.e("Error in encrypted user JSON flow", exception, TAG)
                emit(androidx.datastore.preferences.core.emptyPreferences())
            }
            .map { preferences ->
                try {
                    val encryptedJson = preferences[USER_JSON_KEY]
                    if (encryptedJson != null) {
                        val decryptedJson = encryptionService.decrypt(encryptedJson)
                        if (decryptedJson != null) {
                            json.decodeFromString<User>(decryptedJson)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Logger.e("Error deserializing encrypted user JSON in flow", e, TAG)
                    null
                }
            }
    }

    /**
     * Check if encryption service is working properly
     */
    fun isEncryptionWorking(): Boolean {
        return encryptionService.isInitialized()
    }
}
