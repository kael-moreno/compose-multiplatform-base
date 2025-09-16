package com.kaelmoreno.compose.composemultiplatformbase.di

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import org.koin.core.context.startKoin

/**
 * Initialize Koin for iOS platform
 */
fun initKoin() {
    startKoin {
        modules(appModules)
    }
    Logger.i("Koin initialized for iOS", "iOS")
}
