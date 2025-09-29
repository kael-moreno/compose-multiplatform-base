package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PostsUiState(
    val posts: List<Post> = emptyList(),
    val selectedPost: Post? = null
)

class PostsViewModel(
    private val apiService: ApiService
) : BaseViewModel<PostListScreenSideEffect>() {

    // Own the posts state directly in this ViewModel
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        Logger.i("PostsViewModel initialized with dependency injection", "PostsViewModel")
    }

    fun onAction(action : PostListScreenAction) {
        when(action) {
            PostListScreenAction.OnBackNavigate -> sendSideEffect(PostListScreenSideEffect.BackNavigate)
            PostListScreenAction.OnClearSelectedPost -> clearSelectedPost()
            PostListScreenAction.OnLoadPosts -> loadPosts()
            PostListScreenAction.OnRetry -> retry()
            is PostListScreenAction.OnSelectPost -> selectPost(post = action.post)
        }
    }

    private fun loadPosts() {
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

    private fun selectPost(post: Post) {
        Logger.d("Post selected: ${post.title}", "PostsViewModel")
        _uiState.update { it.copy(selectedPost = post) }
    }

    private fun clearSelectedPost() {
        Logger.d("Clearing selected post", "PostsViewModel")
        _uiState.update { it.copy(selectedPost = null) }
    }

    override fun retry() {
        Logger.i("Retrying to load posts", "PostsViewModel")
        clearError()
        loadPosts()
    }
}
