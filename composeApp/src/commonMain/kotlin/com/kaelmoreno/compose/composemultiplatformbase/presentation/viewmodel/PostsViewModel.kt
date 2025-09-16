package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostsUiState(
    val posts: List<Post> = emptyList(),
    val selectedPost: Post? = null
)

class PostsViewModel : BaseViewModel() {

    private val repository = Repository()

    // UI-specific state (loading/error inherited from BaseViewModel)
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        Logger.i("PostsViewModel initialized", "PostsViewModel")

        // Observe repository data changes
        viewModelScope.launch {
            repository.posts.collect { posts ->
                Logger.d("Repository posts updated: ${posts.size} posts", "PostsViewModel")
                _uiState.value = _uiState.value.copy(posts = posts)
            }
        }
    }

    fun loadPosts() {
        Logger.i("Loading posts requested", "PostsViewModel")
        executeOperationWithFlow(
            operation = { repository.fetchPosts() },
            onSuccess = { posts ->
                Logger.i("Successfully loaded ${posts.size} posts", "PostsViewModel")
                setSuccessMessage("Posts loaded successfully")
            }
        )
    }

    fun loadPostsByUser(userId: Int) {
        Logger.i("Loading posts for user $userId", "PostsViewModel")
        executeOperationWithFlow(
            operation = { repository.fetchPostsByUser(userId) },
            onSuccess = { posts ->
                Logger.i("Successfully loaded ${posts.size} posts for user $userId", "PostsViewModel")
                setSuccessMessage("User posts loaded successfully")
            }
        )
    }

    fun selectPost(post: Post) {
        Logger.d("Post selected: ${post.title}", "PostsViewModel")
        _uiState.value = _uiState.value.copy(selectedPost = post)
    }

    fun clearSelectedPost() {
        Logger.d("Clearing selected post", "PostsViewModel")
        _uiState.value = _uiState.value.copy(selectedPost = null)
    }

    override fun retry() {
        Logger.i("Retrying to load posts", "PostsViewModel")
        clearError()
        loadPosts()
    }
}
