package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.data.network.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository {

    private val apiService = UserApiService()

    // StateFlow for caching users locally
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun fetchUsers() {
        Logger.i("Starting to fetch users", "UserRepository")
        _isLoading.value = true
        _error.value = null

        apiService.getUsers()
            .onSuccess { userList ->
                Logger.i("Repository received ${userList.size} users", "UserRepository")
                _users.value = userList
                _error.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch users", exception, "UserRepository")
                _error.value = exception.message ?: "Unknown error occurred"
            }

        _isLoading.value = false
    }

    suspend fun getUserById(id: Int): Result<User> {
        Logger.d("Getting user by id: $id", "UserRepository")

        // First check if user exists in local cache
        val cachedUser = _users.value.find { it.id == id }
        if (cachedUser != null) {
            Logger.d("User found in cache", "UserRepository")
            return Result.success(cachedUser)
        }

        // If not in cache, fetch from API
        Logger.d("User not in cache, fetching from API", "UserRepository")
        return apiService.getUserById(id)
    }

    fun clearError() {
        _error.value = null
    }
}
