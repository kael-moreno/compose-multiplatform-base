package com.kaelmoreno.compose.composemultiplatformbase.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kaelmoreno.compose.composemultiplatformbase.data.datastore.createDataStore
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.DataStoreRepository
import org.koin.dsl.module

val dataModule = module {

    // DataStore - singleton for preferences storage
    single<DataStore<Preferences>> { createDataStore() }

    // DataStoreRepository - singleton that gets DataStore injected
    single<DataStoreRepository> { DataStoreRepository(get()) }

    // ApiService - singleton that gets Platform injected
    single<ApiService> { ApiService(get()) }
}

// Platform module will be defined separately for each platform
expect val platformModule: org.koin.core.module.Module
