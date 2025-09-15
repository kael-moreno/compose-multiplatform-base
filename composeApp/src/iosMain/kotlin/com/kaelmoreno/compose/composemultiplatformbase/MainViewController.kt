package com.kaelmoreno.compose.composemultiplatformbase

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    // Initialize Napier only once using LaunchedEffect
    LaunchedEffect(Unit) {
        Logger.initialize()
        Logger.i("iOS MainViewController created", "iOS")
    }

    App()
}
