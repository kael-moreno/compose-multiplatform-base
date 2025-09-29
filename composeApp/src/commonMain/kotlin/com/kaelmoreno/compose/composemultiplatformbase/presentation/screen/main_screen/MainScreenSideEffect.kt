package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.main_screen

import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseSideEffect

sealed class MainScreenSideEffect : BaseSideEffect {
    data object NavigateToUsers : MainScreenSideEffect()
    data object NavigateToPosts : MainScreenSideEffect()
}