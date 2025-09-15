package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedUser: User? = null
)

class UserViewModel : ViewModel() {

    private val repository = Repository()

    // Simple StateFlow that we manually update
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        Logger.i("UserViewModel initialized", "UserViewModel")

        // Observe repository changes and update our state
        viewModelScope.launch {
            repository.users.collect { users ->
                Logger.d("Repository users updated: ${users.size} users", "UserViewModel")
                _uiState.value = _uiState.value.copy(users = users)
            }
        }

        viewModelScope.launch {
            repository.isLoadingUsers.collect { isLoading ->
                Logger.d("Repository loading state: $isLoading", "UserViewModel")
                _uiState.value = _uiState.value.copy(isLoading = isLoading)
            }
        }

        viewModelScope.launch {
            repository.userError.collect { error ->
                Logger.d("Repository error: $error", "UserViewModel")
                _uiState.value = _uiState.value.copy(error = error)
            }
        }

        // Don't call loadUsers() here anymore - let the UI control when to load
    }

    fun loadUsers() {
        Logger.i("Loading users requested", "UserViewModel")
        viewModelScope.launch {
            repository.fetchUsers()
        }
    }

    fun selectUser(user: User) {
        Logger.d("User selected: ${user.name}", "UserViewModel")
        Logger.d("Current selected user before: ${_uiState.value.selectedUser?.name}", "UserViewModel")
        _uiState.value = _uiState.value.copy(selectedUser = user)
        Logger.d("Current selected user after: ${_uiState.value.selectedUser?.name}", "UserViewModel")
    }

    fun clearSelectedUser() {
        Logger.d("Clearing selected user", "UserViewModel")
        _uiState.value = _uiState.value.copy(selectedUser = null)
    }

    fun retryLoadUsers() {
        Logger.i("Retrying to load users", "UserViewModel")
        repository.clearUserError()
        loadUsers()
    }

    fun clearError() {
        Logger.d("Clearing error", "UserViewModel")
        repository.clearUserError()
    }
}
