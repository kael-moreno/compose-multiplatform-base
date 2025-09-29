package com.kaelmoreno.compose.composemultiplatformbase.di

import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.main_screen.MainScreenViewModel
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen.PostsViewModel
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.user_list_screen.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    // ViewModels - scoped to the component lifecycle
    viewModel { MainScreenViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { PostsViewModel(get()) }
}
