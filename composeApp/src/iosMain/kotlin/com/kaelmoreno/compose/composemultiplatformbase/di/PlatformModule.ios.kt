package com.kaelmoreno.compose.composemultiplatformbase.di

import com.kaelmoreno.compose.composemultiplatformbase.Platform
import com.kaelmoreno.compose.composemultiplatformbase.createIOSPlatform
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.dsl.module

actual val platformModule = module {
    // Platform provider - singleton for iOS
    single<Platform> { createIOSPlatform() }
    single { KSafe() }
}
