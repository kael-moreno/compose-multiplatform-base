package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.user_list_screen

import com.kaelmoreno.compose.composemultiplatformbase.data.model.User

sealed class UserListScreenAction {
    data object OnBackNavigation : UserListScreenAction()
    data object OnLoadUsers : UserListScreenAction()
    data object OnClearSelectedUser : UserListScreenAction()
    data class OnSelectUser(val user : User) : UserListScreenAction()
    data object OnRetry : UserListScreenAction()
}