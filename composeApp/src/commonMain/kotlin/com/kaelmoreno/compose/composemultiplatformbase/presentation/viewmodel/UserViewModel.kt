package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserUiState(
    val users: List<User> = emptyList(),
    val selectedUser: User? = null
)

class UserViewModel(
    private val apiService: ApiService
) : BaseViewModel() {

    // Own the user state directly in this ViewModel
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        Logger.i("UserViewModel initialized with dependency injection", "UserViewModel")
    }

    fun loadUsers() {
        Logger.i("Loading users requested", "UserViewModel")
        executeOperationWithFlow(
            operation = { apiService.getUsers() },
            onSuccess = { users ->
                Logger.i("Successfully loaded ${users.size} users", "UserViewModel")
                _uiState.update { it.copy(users = users) }
                setSuccessMessage("Users loaded successfully")
            }
        )
    }

    fun selectUser(user: User) {
        Logger.d("User selected: ${user.name}", "UserViewModel")
        _uiState.update { it.copy(selectedUser = user) }
    }

    fun clearSelectedUser() {
        Logger.d("Clearing selected user", "UserViewModel")
        _uiState.update { it.copy(selectedUser = null) }
    }

    override fun retry() {
        Logger.i("Retrying to load users", "UserViewModel")
        clearError()
        loadUsers()
    }
}
