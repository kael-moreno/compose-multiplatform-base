package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.data.model.UserPreferences
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.SecureStorageRepository
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MainScreenUiState(
    val savedToken: String? = null,
    val savedPreferences: UserPreferences? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

class MainScreenViewModel(
    private val secureStorageRepository: SecureStorageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        loadStoredData()
    }

    private fun loadStoredData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val token = secureStorageRepository.getUserToken()
                val preferences = secureStorageRepository.getUserPreferences()

                _uiState.value = _uiState.value.copy(
                    savedToken = token,
                    savedPreferences = preferences,
                    isLoading = false,
                    message = if (token != null || preferences != null) "Data loaded from secure storage" else "No stored data found"
                )

                Logger.d("Loaded data - Token: ${token != null}, Preferences: ${preferences != null}", "MainScreenViewModel")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error loading data: ${e.message}"
                )
                Logger.e("Error loading stored data", e, "MainScreenViewModel")
            }
        }
    }

    fun saveStringDemo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val demoToken = "demo_user_token_${Random.nextLong()}"
                val success = secureStorageRepository.saveUserToken(demoToken)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = demoToken,
                        isLoading = false,
                        message = "String saved successfully!"
                    )
                    Logger.d("String saved: $demoToken", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to save string"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error saving string: ${e.message}"
                )
                Logger.e("Error saving string", e, "MainScreenViewModel")
            }
        }
    }

    fun saveJsonDemo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val demoPreferences = UserPreferences(
                    username = "demo_user",
                    theme = "dark",
                    notificationsEnabled = true,
                    language = "en"
                )

                val success = secureStorageRepository.saveUserPreferences(demoPreferences)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedPreferences = demoPreferences,
                        isLoading = false,
                        message = "JSON saved successfully!"
                    )
                    Logger.d("JSON saved: $demoPreferences", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to save JSON"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error saving JSON: ${e.message}"
                )
                Logger.e("Error saving JSON", e, "MainScreenViewModel")
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val success = secureStorageRepository.clearAllData()

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = null,
                        savedPreferences = null,
                        isLoading = false,
                        message = "All data cleared successfully!"
                    )
                    Logger.d("All data cleared", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to clear data"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error clearing data: ${e.message}"
                )
                Logger.e("Error clearing data", e, "MainScreenViewModel")
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}
