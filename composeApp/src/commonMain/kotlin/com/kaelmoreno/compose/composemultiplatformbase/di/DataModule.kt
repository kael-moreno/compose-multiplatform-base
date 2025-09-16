package com.kaelmoreno.compose.composemultiplatformbase.di

import com.kaelmoreno.compose.composemultiplatformbase.Platform
import com.kaelmoreno.compose.composemultiplatformbase.getPlatform
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import org.koin.dsl.module

val dataModule = module {
    // Platform provider - singleton
    single<Platform> { getPlatform() }

    // ApiService - singleton
    single<ApiService> { ApiService() }
}
