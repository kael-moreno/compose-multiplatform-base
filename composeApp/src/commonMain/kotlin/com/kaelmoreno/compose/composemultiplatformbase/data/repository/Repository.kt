package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class Repository {

    private val apiService = ApiService()

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
    suspend fun fetchUsers(): Result<List<User>> {
        Logger.i("Starting to fetch users", "Repository")

        return apiService.getUsers()
            .onSuccess { userList ->
                Logger.i("Repository received ${userList.size} users", "Repository")
                _users.value = userList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch users", exception, "Repository")
            }
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
    suspend fun fetchPosts(): Result<List<Post>> {
        Logger.i("Starting to fetch posts", "Repository")

        return apiService.getPosts()
            .onSuccess { postList ->
                Logger.i("Repository received ${postList.size} posts", "Repository")
                _posts.value = postList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch posts", exception, "Repository")
            }
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

    suspend fun fetchPostsByUser(userId: Int): Result<List<Post>> {
        Logger.i("Starting to fetch posts for user: $userId", "Repository")

        return apiService.getPostsByUser(userId)
            .onSuccess { postList ->
                Logger.i("Repository received ${postList.size} posts for user $userId", "Repository")
                _posts.value = postList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch posts for user $userId", exception, "Repository")
            }
    }

    // Comments Methods
    suspend fun fetchComments(): Result<List<Comment>> {
        Logger.i("Starting to fetch comments", "Repository")

        return apiService.getComments()
            .onSuccess { commentList ->
                Logger.i("Repository received ${commentList.size} comments", "Repository")
                _comments.value = commentList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch comments", exception, "Repository")
            }
    }

    suspend fun fetchCommentsByPost(postId: Int): Result<List<Comment>> {
        Logger.i("Starting to fetch comments for post: $postId", "Repository")

        return apiService.getCommentsByPost(postId)
            .onSuccess { commentList ->
                Logger.i("Repository received ${commentList.size} comments for post $postId", "Repository")
                _comments.value = commentList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch comments for post $postId", exception, "Repository")
            }
    }

    // Albums Methods
    suspend fun fetchAlbums(): Result<List<Album>> {
        Logger.i("Starting to fetch albums", "Repository")

        return apiService.getAlbums()
            .onSuccess { albumList ->
                Logger.i("Repository received ${albumList.size} albums", "Repository")
                _albums.value = albumList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch albums", exception, "Repository")
            }
    }

    suspend fun fetchAlbumsByUser(userId: Int): Result<List<Album>> {
        Logger.i("Starting to fetch albums for user: $userId", "Repository")

        return apiService.getAlbumsByUser(userId)
            .onSuccess { albumList ->
                Logger.i("Repository received ${albumList.size} albums for user $userId", "Repository")
                _albums.value = albumList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch albums for user $userId", exception, "Repository")
            }
    }

    // Photos Methods
    suspend fun fetchPhotosByAlbum(albumId: Int): Result<List<Photo>> {
        Logger.i("Starting to fetch photos for album: $albumId", "Repository")

        return apiService.getPhotosByAlbum(albumId)
            .onSuccess { photoList ->
                Logger.i("Repository received ${photoList.size} photos for album $albumId", "Repository")
                _photos.value = photoList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch photos for album $albumId", exception, "Repository")
            }
    }

    // Todos Methods
    suspend fun fetchTodos(): Result<List<Todo>> {
        Logger.i("Starting to fetch todos", "Repository")

        return apiService.getTodos()
            .onSuccess { todoList ->
                Logger.i("Repository received ${todoList.size} todos", "Repository")
                _todos.value = todoList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch todos", exception, "Repository")
            }
    }

    suspend fun fetchTodosByUser(userId: Int): Result<List<Todo>> {
        Logger.i("Starting to fetch todos for user: $userId", "Repository")

        return apiService.getTodosByUser(userId)
            .onSuccess { todoList ->
                Logger.i("Repository received ${todoList.size} todos for user $userId", "Repository")
                _todos.value = todoList
            }
            .onFailure { exception ->
                Logger.e("Repository failed to fetch todos for user $userId", exception, "Repository")
            }
    }
}
