package com.kaelmoreno.compose.composemultiplatformbase.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kaelmoreno.compose.composemultiplatformbase.data.datastore.createDataStore
import com.kaelmoreno.compose.composemultiplatformbase.data.encryption.EncryptionService
import com.kaelmoreno.compose.composemultiplatformbase.data.encryption.createEncryptionService
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.DataStoreRepository
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.EncryptedDataStoreRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {

    // DataStore - singleton for preferences storage
    singleOf(::createDataStore)

    // Encryption Service - singleton for encrypting/decrypting data
    singleOf(::createEncryptionService)

    // DataStoreRepository - singleton that gets DataStore injected (original, unencrypted)
    singleOf(::DataStoreRepository)

    // EncryptedDataStoreRepository - singleton that gets DataStore and EncryptionService injected
    singleOf(::EncryptedDataStoreRepository)

    // ApiService - singleton that gets Platform injected
    singleOf(::ApiService)
}

// Platform module will be defined separately for each platform
expect val platformModule: Module
