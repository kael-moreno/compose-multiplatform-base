package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

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
    val selectedUser: User? = null
)

class UserViewModel : BaseViewModel() {

    private val repository = Repository()

    // UI-specific state (no loading/error needed)
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        Logger.i("UserViewModel initialized", "UserViewModel")

        // Observe repository data changes
        viewModelScope.launch {
            repository.users.collect { users ->
                Logger.d("Repository users updated: ${users.size} users", "UserViewModel")
                _uiState.value = _uiState.value.copy(users = users)
            }
        }
    }

    fun loadUsers() {
        Logger.i("Loading users requested", "UserViewModel")
        executeOperationWithFlow(
            operation = { repository.fetchUsers() },
            onSuccess = { users ->
                Logger.i("Successfully loaded ${users.size} users", "UserViewModel")
                repository.updateUsers(users) // Update repository state
                setSuccessMessage("Users loaded successfully")
            }
        )
    }

    fun selectUser(user: User) {
        Logger.d("User selected: ${user.name}", "UserViewModel")
        _uiState.value = _uiState.value.copy(selectedUser = user)
    }

    fun clearSelectedUser() {
        Logger.d("Clearing selected user", "UserViewModel")
        _uiState.value = _uiState.value.copy(selectedUser = null)
    }

    override fun retry() {
        Logger.i("Retrying to load users", "UserViewModel")
        clearError()
        loadUsers()
    }

    fun retryLoadUsers() = retry() // Alias for backward compatibility
}
