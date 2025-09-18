package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.DataStoreRepository
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.EncryptedDataStoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MainScreenUiState(
    val savedToken: String? = null,
    val savedUserName: String? = null,
    val savedUser: User? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)

class MainScreenViewModel(
    private val dataStoreRepository: EncryptedDataStoreRepository
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
                val dataStoreToken = dataStoreRepository.getUserToken()
                val dataStoreUserName = dataStoreRepository.getUserName()
                val dataStoreUser = dataStoreRepository.getUser()

                val hasDataStoreData = dataStoreToken != null || dataStoreUserName != null || dataStoreUser != null

                _uiState.value = _uiState.value.copy(
                    savedToken = dataStoreToken,
                    savedUserName = dataStoreUserName,
                    savedUser = dataStoreUser,
                    isLoading = false,
                    message = if (hasDataStoreData) "Data loaded from DataStore" else "No stored data found"
                )

                Logger.d("Loaded data - Token: ${dataStoreToken != null}, UserName: ${dataStoreUserName != null}, User JSON: ${dataStoreUser != null}", "MainScreenViewModel")
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
                val demoToken = "datastore_token_${Random.nextLong()}"
                val success = dataStoreRepository.saveUserToken(demoToken)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = demoToken,
                        isLoading = false,
                        message = "String saved successfully using DataStore!"
                    )
                    Logger.d("String saved using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to save string using DataStore"
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

    fun saveUserNameDemo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val demoUserName = "datastore_user_${Random.nextInt(1000)}"
                val success = dataStoreRepository.saveUserName(demoUserName)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedUserName = demoUserName,
                        isLoading = false,
                        message = "User name saved successfully using DataStore!"
                    )
                    Logger.d("User name saved using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to save user name using DataStore"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error saving user name: ${e.message}"
                )
                Logger.e("Error saving user name", e, "MainScreenViewModel")
            }
        }
    }

    fun saveUserJsonDemo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val demoUser = User(
                    id = Random.nextInt(1000),
                    name = "John Doe ${Random.nextInt(100)}",
                    phone = "+1${Random.nextLong(1000000000, 9999999999)}",
                    username = null,
                    email = null,
                    address = null,
                    website = null,
                    company = null
                )

                val success = dataStoreRepository.saveUser(demoUser)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedUser = demoUser,
                        isLoading = false,
                        message = "User JSON saved successfully using DataStore!"
                    )
                    Logger.d("User JSON saved using DataStore: $demoUser", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to save user JSON using DataStore"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    message = "Error saving user JSON: ${e.message}"
                )
                Logger.e("Error saving user JSON", e, "MainScreenViewModel")
            }
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val success = dataStoreRepository.clearAllData()

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = null,
                        savedUserName = null,
                        savedUser = null,
                        isLoading = false,
                        message = "All data cleared successfully using DataStore!"
                    )
                    Logger.d("All data cleared using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        message = "Failed to clear data using DataStore"
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
