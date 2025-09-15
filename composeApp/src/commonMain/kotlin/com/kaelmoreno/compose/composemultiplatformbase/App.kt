package com.kaelmoreno.compose.composemultiplatformbase

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kaelmoreno.compose.composemultiplatformbase.navigation.AppNavigation

@Composable
fun App() {
    // Initialize logger when App composable is first created
    LaunchedEffect(Unit) {
        Logger.i("App composable initialized with NavHost navigation", "App")
    }

    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavigation(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}