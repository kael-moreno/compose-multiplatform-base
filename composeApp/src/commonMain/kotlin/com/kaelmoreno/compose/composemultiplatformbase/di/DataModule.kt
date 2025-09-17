package com.kaelmoreno.compose.composemultiplatformbase.di

import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.SecureStorageRepository
import org.koin.dsl.module

val dataModule = module {
    // ApiService - singleton that gets Platform injected
    single<ApiService> { ApiService(get()) }
    single { SecureStorageRepository(get()) }
}

// Platform module will be defined separately for each platform
expect val platformModule: org.koin.core.module.Module
