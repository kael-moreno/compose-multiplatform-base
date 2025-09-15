package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Repository {

    private val apiService = ApiService()

    // User State Management
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _isLoadingUsers = MutableStateFlow(false)
    val isLoadingUsers: StateFlow<Boolean> = _isLoadingUsers.asStateFlow()

    private val _userError = MutableStateFlow<String?>(null)
    val userError: StateFlow<String?> = _userError.asStateFlow()

    // Posts State Management
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _isLoadingPosts = MutableStateFlow(false)
    val isLoadingPosts: StateFlow<Boolean> = _isLoadingPosts.asStateFlow()

    private val _postError = MutableStateFlow<String?>(null)
    val postError: StateFlow<String?> = _postError.asStateFlow()

    // Comments State Management
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    private val _isLoadingComments = MutableStateFlow(false)
    val isLoadingComments: StateFlow<Boolean> = _isLoadingComments.asStateFlow()

    private val _commentError = MutableStateFlow<String?>(null)
    val commentError: StateFlow<String?> = _commentError.asStateFlow()

    // Albums State Management
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _isLoadingAlbums = MutableStateFlow(false)
    val isLoadingAlbums: StateFlow<Boolean> = _isLoadingAlbums.asStateFlow()

    private val _albumError = MutableStateFlow<String?>(null)
    val albumError: StateFlow<String?> = _albumError.asStateFlow()

    // Photos State Management
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos.asStateFlow()

    private val _isLoadingPhotos = MutableStateFlow(false)
    val isLoadingPhotos: StateFlow<Boolean> = _isLoadingPhotos.asStateFlow()

    private val _photoError = MutableStateFlow<String?>(null)
    val photoError: StateFlow<String?> = _photoError.asStateFlow()

    // Todos State Management
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()

    private val _isLoadingTodos = MutableStateFlow(false)
    val isLoadingTodos: StateFlow<Boolean> = _isLoadingTodos.asStateFlow()

    private val _todoError = MutableStateFlow<String?>(null)
    val todoError: StateFlow<String?> = _todoError.asStateFlow()

    // User Methods
    suspend fun fetchUsers() {
        Logger.i("Starting to fetch users", "Repository")
        _isLoadingUsers.value = true
        _userError.value = null

        apiService.getUsers()
            .onSuccess { userList ->
                Logger.i("Repository received ${userList.size} users", "Repository")
                _users.value = userList
                _userError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch users", exception, "Repository")
                _userError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingUsers.value = false
    }

    suspend fun getUserById(id: Int): Result<User> {
        Logger.d("Getting user by id: $id", "Repository")

        // First check if user exists in local cache
        val cachedUser = _users.value.find { it.id == id }
        if (cachedUser != null) {
            Logger.d("User found in cache", "Repository")
            return Result.success(cachedUser)
        }

        // If not in cache, fetch from API
        Logger.d("User not in cache, fetching from API", "Repository")
        return apiService.getUserById(id)
    }

    // Posts Methods
    suspend fun fetchPosts() {
        Logger.i("Starting to fetch posts", "Repository")
        _isLoadingPosts.value = true
        _postError.value = null

        apiService.getPosts()
            .onSuccess { postList ->
                Logger.i("Repository received ${postList.size} posts", "Repository")
                _posts.value = postList
                _postError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch posts", exception, "Repository")
                _postError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingPosts.value = false
    }

    suspend fun getPostById(id: Int): Result<Post> {
        Logger.d("Getting post by id: $id", "Repository")

        // First check if post exists in local cache
        val cachedPost = _posts.value.find { it.id == id }
        if (cachedPost != null) {
            Logger.d("Post found in cache", "Repository")
            return Result.success(cachedPost)
        }

        // If not in cache, fetch from API
        Logger.d("Post not in cache, fetching from API", "Repository")
        return apiService.getPostById(id)
    }

    suspend fun fetchPostsByUser(userId: Int) {
        Logger.i("Starting to fetch posts for user: $userId", "Repository")
        _isLoadingPosts.value = true
        _postError.value = null

        apiService.getPostsByUser(userId)
            .onSuccess { postList ->
                Logger.i("Repository received ${postList.size} posts for user $userId", "Repository")
                _posts.value = postList
                _postError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch posts for user $userId", exception, "Repository")
                _postError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingPosts.value = false
    }

    // Comments Methods
    suspend fun fetchComments() {
        Logger.i("Starting to fetch comments", "Repository")
        _isLoadingComments.value = true
        _commentError.value = null

        apiService.getComments()
            .onSuccess { commentList ->
                Logger.i("Repository received ${commentList.size} comments", "Repository")
                _comments.value = commentList
                _commentError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch comments", exception, "Repository")
                _commentError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingComments.value = false
    }

    suspend fun fetchCommentsByPost(postId: Int) {
        Logger.i("Starting to fetch comments for post: $postId", "Repository")
        _isLoadingComments.value = true
        _commentError.value = null

        apiService.getCommentsByPost(postId)
            .onSuccess { commentList ->
                Logger.i("Repository received ${commentList.size} comments for post $postId", "Repository")
                _comments.value = commentList
                _commentError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch comments for post $postId", exception, "Repository")
                _commentError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingComments.value = false
    }

    // Albums Methods
    suspend fun fetchAlbums() {
        Logger.i("Starting to fetch albums", "Repository")
        _isLoadingAlbums.value = true
        _albumError.value = null

        apiService.getAlbums()
            .onSuccess { albumList ->
                Logger.i("Repository received ${albumList.size} albums", "Repository")
                _albums.value = albumList
                _albumError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch albums", exception, "Repository")
                _albumError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingAlbums.value = false
    }

    suspend fun fetchAlbumsByUser(userId: Int) {
        Logger.i("Starting to fetch albums for user: $userId", "Repository")
        _isLoadingAlbums.value = true
        _albumError.value = null

        apiService.getAlbumsByUser(userId)
            .onSuccess { albumList ->
                Logger.i("Repository received ${albumList.size} albums for user $userId", "Repository")
                _albums.value = albumList
                _albumError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch albums for user $userId", exception, "Repository")
                _albumError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingAlbums.value = false
    }

    // Photos Methods
    suspend fun fetchPhotosByAlbum(albumId: Int) {
        Logger.i("Starting to fetch photos for album: $albumId", "Repository")
        _isLoadingPhotos.value = true
        _photoError.value = null

        apiService.getPhotosByAlbum(albumId)
            .onSuccess { photoList ->
                Logger.i("Repository received ${photoList.size} photos for album $albumId", "Repository")
                _photos.value = photoList
                _photoError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch photos for album $albumId", exception, "Repository")
                _photoError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingPhotos.value = false
    }

    // Todos Methods
    suspend fun fetchTodos() {
        Logger.i("Starting to fetch todos", "Repository")
        _isLoadingTodos.value = true
        _todoError.value = null

        apiService.getTodos()
            .onSuccess { todoList ->
                Logger.i("Repository received ${todoList.size} todos", "Repository")
                _todos.value = todoList
                _todoError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch todos", exception, "Repository")
                _todoError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingTodos.value = false
    }

    suspend fun fetchTodosByUser(userId: Int) {
        Logger.i("Starting to fetch todos for user: $userId", "Repository")
        _isLoadingTodos.value = true
        _todoError.value = null

        apiService.getTodosByUser(userId)
            .onSuccess { todoList ->
                Logger.i("Repository received ${todoList.size} todos for user $userId", "Repository")
                _todos.value = todoList
                _todoError.value = null
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch todos for user $userId", exception, "Repository")
                _todoError.value = exception.message ?: "Unknown error occurred"
            }

        _isLoadingTodos.value = false
    }

    // Error clearing methods
    fun clearUserError() {
        _userError.value = null
    }

    fun clearPostError() {
        _postError.value = null
    }

    fun clearCommentError() {
        _commentError.value = null
    }

    fun clearAlbumError() {
        _albumError.value = null
    }

    fun clearPhotoError() {
        _photoError.value = null
    }

    fun clearTodoError() {
        _todoError.value = null
    }

    fun clearAllErrors() {
        clearUserError()
        clearPostError()
        clearCommentError()
        clearAlbumError()
        clearPhotoError()
        clearTodoError()
    }
}
