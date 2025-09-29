package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen

import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseSideEffect

sealed class PostListScreenSideEffect : BaseSideEffect {
    data object BackNavigate : PostListScreenSideEffect()
}