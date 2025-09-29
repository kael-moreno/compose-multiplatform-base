package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.main_screen

import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.EncryptedDataStoreRepository
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MainScreenUiState(
    val savedToken: String? = null,
    val savedUserName: String? = null,
    val savedUser: User? = null,
    val message: String? = null
)

class MainScreenViewModel(
    private val dataStoreRepository: EncryptedDataStoreRepository
) : BaseViewModel<MainScreenSideEffect>() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        loadStoredData()
    }

    fun onAction(mainScreenAction: MainScreenAction) {
        when (mainScreenAction) {
            MainScreenAction.OnNavigateToPosts -> sendSideEffect(MainScreenSideEffect.NavigateToPosts)
            MainScreenAction.OnNavigateToUsers -> sendSideEffect(MainScreenSideEffect.NavigateToUsers)
            MainScreenAction.OnClearAllData -> clearAllData()
            MainScreenAction.OnSaveStringDemo -> saveStringDemo()
            MainScreenAction.OnSaveUserJsonDemo -> saveUserJsonDemo()
            MainScreenAction.OnSaveUserNameDemo -> saveUserNameDemo()
            MainScreenAction.OnClearMessage -> clearMessage()
        }
    }

    private fun loadStoredData() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val dataStoreToken = dataStoreRepository.getUserToken()
                val dataStoreUserName = dataStoreRepository.getUserName()
                val dataStoreUser = dataStoreRepository.getUser()

                val hasDataStoreData =
                    dataStoreToken != null || dataStoreUserName != null || dataStoreUser != null

                _uiState.value = _uiState.value.copy(
                    savedToken = dataStoreToken,
                    savedUserName = dataStoreUserName,
                    savedUser = dataStoreUser,
                    message = if (hasDataStoreData) "Data loaded from DataStore" else "No stored data found"
                )

                setLoading(false)

                Logger.d(
                    "Loaded data - Token: ${dataStoreToken != null}, UserName: ${dataStoreUserName != null}, User JSON: ${dataStoreUser != null}",
                    "MainScreenViewModel"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error loading data: ${e.message}"
                )
                setLoading(false)
                Logger.e("Error loading stored data", e, "MainScreenViewModel")
            }
        }
    }

    private fun saveStringDemo() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val demoToken = "datastore_token_${Random.nextLong()}"
                val success = dataStoreRepository.saveUserToken(demoToken)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = demoToken,
                        message = "String saved successfully using DataStore!"
                    )
                    Logger.d("String saved using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        message = "Failed to save string using DataStore"
                    )
                }

                setLoading(false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error saving string: ${e.message}"
                )

                setLoading(false)
                Logger.e("Error saving string", e, "MainScreenViewModel")
            }
        }
    }

    private fun saveUserNameDemo() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val demoUserName = "datastore_user_${Random.nextInt(1000)}"
                val success = dataStoreRepository.saveUserName(demoUserName)

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedUserName = demoUserName,
                        message = "User name saved successfully using DataStore!"
                    )
                    Logger.d("User name saved using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        message = "Failed to save user name using DataStore"
                    )
                }

                setLoading(false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error saving user name: ${e.message}"
                )
                setLoading(false)
                Logger.e("Error saving user name", e, "MainScreenViewModel")
            }
        }
    }

    private fun saveUserJsonDemo() {
        viewModelScope.launch {
            setLoading(true)
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
                        message = "User JSON saved successfully using DataStore!"
                    )
                    Logger.d("User JSON saved using DataStore: $demoUser", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        message = "Failed to save user JSON using DataStore"
                    )
                }

                setLoading(false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error saving user JSON: ${e.message}"
                )
                setLoading(false)
                Logger.e("Error saving user JSON", e, "MainScreenViewModel")
            }
        }
    }

    private fun clearAllData() {
        viewModelScope.launch {
            setLoading(true)
            try {
                val success = dataStoreRepository.clearAllData()

                if (success) {
                    _uiState.value = _uiState.value.copy(
                        savedToken = null,
                        savedUserName = null,
                        savedUser = null,
                        message = "All data cleared successfully using DataStore!"
                    )
                    Logger.d("All data cleared using DataStore", "MainScreenViewModel")
                } else {
                    _uiState.value = _uiState.value.copy(
                        message = "Failed to clear data using DataStore"
                    )
                }

                setLoading(false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    message = "Error clearing data: ${e.message}"
                )

                setLoading(false)
                Logger.e("Error clearing data", e, "MainScreenViewModel")
            }
        }
    }

    private fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    override fun retry() {
        //do nothing
    }
}
