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
import kotlinx.coroutines.flow.onEach

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

    // Comments State Management (only data, no loading/error)
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    // Albums State Management (only data, no loading/error)
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    // Photos State Management (only data, no loading/error)
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    // Todos State Management (only data, no loading/error)
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    // User Methods
    fun fetchUsers(): Flow<ResponseHandler<List<User>>> {
        Logger.i("Starting to fetch users", "Repository")

        return apiService.getUsers()
            .onEach { response ->
                when (response) {
                    is ResponseHandler.Success -> {
                        response.result?.let { userList ->
                            Logger.i("Repository received ${userList.size} users", "Repository")
                            _users.value = userList
                        }
                    }
                    is ResponseHandler.Error -> {
                        Logger.e("Repository failed to fetch users: ${response.apiError?.error?.message}", null, "Repository")
                    }
                    is ResponseHandler.Failure -> {
                        Logger.e("Repository failed to fetch users", response.exception, "Repository")
                    }
                    is ResponseHandler.Loading -> {
                        Logger.d("Loading users...", "Repository")
                    }
                }
            }
    }

    // Posts Methods
    fun fetchPosts(): Flow<ResponseHandler<List<Post>>> {
        Logger.i("Starting to fetch posts", "Repository")

        return apiService.getPosts()
            .onEach { response ->
                when (response) {
                    is ResponseHandler.Success -> {
                        response.result?.let { postList ->
                            Logger.i("Repository received ${postList.size} posts", "Repository")
                            _posts.value = postList
                        }
                    }
                    is ResponseHandler.Error -> {
                        Logger.e("Repository failed to fetch posts: ${response.apiError?.error?.message}", null, "Repository")
                    }
                    is ResponseHandler.Failure -> {
                        Logger.e("Repository failed to fetch posts", response.exception, "Repository")
                    }
                    is ResponseHandler.Loading -> {
                        Logger.d("Loading posts...", "Repository")
                    }
                }
            }
    }

    fun fetchPostsByUser(userId: Int): Flow<ResponseHandler<List<Post>>> {
        Logger.i("Starting to fetch posts for user: $userId", "Repository")

        return apiService.getPostsByUser(userId)
            .onEach { response ->
                when (response) {
                    is ResponseHandler.Success -> {
                        response.result?.let { postList ->
                            Logger.i("Repository received ${postList.size} posts for user $userId", "Repository")
                            _posts.value = postList
                        }
                    }
                    is ResponseHandler.Error -> {
                        Logger.e("Repository failed to fetch posts for user $userId: ${response.apiError?.error?.message}", null, "Repository")
                    }
                    is ResponseHandler.Failure -> {
                        Logger.e("Repository failed to fetch posts for user $userId", response.exception, "Repository")
                    }
                    is ResponseHandler.Loading -> {
                        Logger.d("Loading posts for user $userId...", "Repository")
                    }
                }
            }
    }

    // Helper method to update API key after authentication
    fun updateApiKey(apiKey: String) {
        platform.apiKey = apiKey
        apiService.updateApiKey(apiKey)
    }
}
