package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PostsUiState(
    val posts: List<Post> = emptyList(),
    val selectedPost: Post? = null
)

class PostsViewModel : BaseViewModel() {

    private val apiService = ApiService()

    // Own the posts state directly in this ViewModel
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        Logger.i("PostsViewModel initialized", "PostsViewModel")
    }

    fun loadPosts() {
        Logger.i("Loading posts requested", "PostsViewModel")
        executeOperationWithFlow(
            operation = { apiService.getPosts() },
            onSuccess = { posts ->
                Logger.i("Successfully loaded ${posts.size} posts", "PostsViewModel")
                _uiState.update { it.copy(posts = posts) }
                setSuccessMessage("Posts loaded successfully")
            }
        )
    }

    fun loadPostsByUser(userId: Int) {
        Logger.i("Loading posts for user: $userId", "PostsViewModel")
        executeOperationWithFlow(
            operation = { apiService.getPostsByUser(userId) },
            onSuccess = { posts: List<Post> ->
                Logger.i("Successfully loaded ${posts.size} posts for user: $userId", "PostsViewModel")
                _uiState.update { it.copy(posts = posts) }
                setSuccessMessage("Posts loaded successfully")
            }
        )
    }

    fun selectPost(post: Post) {
        Logger.d("Post selected: ${post.title}", "PostsViewModel")
        _uiState.update { it.copy(selectedPost = post) }
    }

    fun clearSelectedPost() {
        Logger.d("Clearing selected post", "PostsViewModel")
        _uiState.update { it.copy(selectedPost = null) }
    }

    override fun retry() {
        Logger.i("Retrying to load posts", "PostsViewModel")
        clearError()
        loadPosts()
    }

    fun retryLoadPosts() = retry() // Alias for backward compatibility
}
