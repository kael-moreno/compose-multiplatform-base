package com.kaelmoreno.compose.composemultiplatformbase.di

import org.koin.dsl.module

/**
 * Main Koin module that includes all application modules.
 * This makes it easy to start Koin with all dependencies configured.
 */
val appModules = module {
    includes(
        platformModule,
        dataModule,
        presentationModule
    )
}
