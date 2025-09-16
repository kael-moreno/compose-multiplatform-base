package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.getPlatform
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ResponseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.Flow

class Repository {

    private val apiService = ApiService()

    // Platform instance for direct network calls
    private val platform = getPlatform()

    // User State Management (only data, no loading/error)
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    // Posts State Management (only data, no loading/error)
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    // User Methods
    fun fetchUsers(): Flow<ResponseHandler<List<User>>> {
        Logger.i("Starting to fetch users", "Repository")
        return apiService.getUsers()
    }

    // Posts Methods
    fun fetchPosts(): Flow<ResponseHandler<List<Post>>> {
        Logger.i("Starting to fetch posts", "Repository")
        return apiService.getPosts()
    }

    // State update methods to be called from ViewModels after successful operations
    fun updateUsers(users: List<User>) {
        Logger.i("Repository updating users state: ${users.size} users", "Repository")
        _users.value = users
    }

    // Helper method to update API key after authentication
    fun updateApiKey(apiKey: String) {
        platform.apiKey = apiKey
        apiService.updateApiKey(apiKey)
    }
}
