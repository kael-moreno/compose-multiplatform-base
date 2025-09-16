package com.kaelmoreno.compose.composemultiplatformbase

import com.kaelmoreno.compose.composemultiplatformbase.Logger

/**
 * Android implementation of Koin initialization.
 * Koin is already initialized in MainActivity, so this is a no-op.
 */
actual fun initializeKoinForPlatform() {
    // Koin is already initialized in MainActivity for Android
    Logger.d("Koin already initialized in MainActivity for Android", "Koin")
}
