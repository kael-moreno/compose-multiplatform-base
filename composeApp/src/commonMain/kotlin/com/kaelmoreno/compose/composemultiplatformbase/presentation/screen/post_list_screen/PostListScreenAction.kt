package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen

import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post

sealed class PostListScreenAction {
    data object OnBackNavigate : PostListScreenAction()
    data object OnLoadPosts : PostListScreenAction()
    data object OnClearSelectedPost : PostListScreenAction()
    data class OnSelectPost(val post : Post) : PostListScreenAction()
    data object OnRetry : PostListScreenAction()

}