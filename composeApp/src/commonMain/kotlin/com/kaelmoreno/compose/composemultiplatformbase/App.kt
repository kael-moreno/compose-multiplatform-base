package com.kaelmoreno.compose.composemultiplatformbase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.UserListScreen
import com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel.UserViewModel

@Composable
fun App() {
    // Initialize logger when App composable is first created
    LaunchedEffect(Unit) {
        Logger.i("App composable initialized", "App")
    }

    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            val userViewModel: UserViewModel = viewModel { UserViewModel() }
            UserListScreen(
                viewModel = userViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}