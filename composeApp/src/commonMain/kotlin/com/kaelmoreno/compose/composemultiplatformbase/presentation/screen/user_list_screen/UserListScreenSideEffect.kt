package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.user_list_screen

import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseSideEffect

sealed class UserListScreenSideEffect : BaseSideEffect {
    data object BackNavigate : UserListScreenSideEffect()
}