package com.kaelmoreno.compose.composemultiplatformbase.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.mp.KoinPlatform.getKoin

internal const val dataStoreFileName = "dice.preferences_pb"

actual fun createDataStore(): DataStore<Preferences> {
    val context = getKoin().get<Context>()
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { context.filesDir.resolve(dataStoreFileName).absolutePath.toPath() }
    )
}
