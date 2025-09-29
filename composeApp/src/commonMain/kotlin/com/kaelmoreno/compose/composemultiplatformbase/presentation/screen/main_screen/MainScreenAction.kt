package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.main_screen

sealed class MainScreenAction {
    data object OnNavigateToUsers : MainScreenAction()
    data object OnNavigateToPosts : MainScreenAction()
    data object OnSaveStringDemo : MainScreenAction()
    data object OnSaveUserNameDemo : MainScreenAction()
    data object OnSaveUserJsonDemo : MainScreenAction()
    data object OnClearAllData : MainScreenAction()
    data object OnClearMessage : MainScreenAction()
}