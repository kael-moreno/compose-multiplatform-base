package com.kaelmoreno.compose.composemultiplatformbase

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kaelmoreno.compose.composemultiplatformbase.navigation.AppNavigation
import com.kaelmoreno.compose.composemultiplatformbase.ui.theme.GitHubDarkColorScheme
import com.kaelmoreno.compose.composemultiplatformbase.ui.theme.GitHubLightColorScheme

@Composable
fun App() {
    // Initialize logger when App composable is first created
    LaunchedEffect(Unit) {
        Logger.i("App composable initialized with GitHub color scheme", "App")

        // Initialize Koin for iOS (Android is initialized in MainActivity)
        initializeKoinForPlatform()
    }

    // Use GitHub-inspired color scheme
    val colorScheme = if (isSystemInDarkTheme()) {
        GitHubDarkColorScheme
    } else {
        GitHubLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

expect fun initializeKoinForPlatform()
