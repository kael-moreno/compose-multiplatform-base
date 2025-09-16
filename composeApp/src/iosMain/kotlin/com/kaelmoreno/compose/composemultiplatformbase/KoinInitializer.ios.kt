package com.kaelmoreno.compose.composemultiplatformbase

import com.kaelmoreno.compose.composemultiplatformbase.di.initKoin

/**
 * iOS implementation of Koin initialization.
 * Initializes Koin with the app modules for iOS platform.
 */
actual fun initializeKoinForPlatform() {
    initKoin()
}
