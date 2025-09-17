package com.kaelmoreno.compose.composemultiplatformbase.di

import android.content.Context
import com.kaelmoreno.compose.composemultiplatformbase.AndroidPlatform
import com.kaelmoreno.compose.composemultiplatformbase.BuildConfig
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.Platform
import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import eu.anifantakis.lib.ksafe.KSafe
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.UUID

actual val platformModule = module {
    // Platform provider - singleton that gets Android Context injected by Koin
    single<Platform> {
        val context: Context = get() // Koin automatically provides Android Context
        createAndroidPlatform(context)
    }

    single { KSafe(androidContext()) }
}

private fun createAndroidPlatform(context: Context): Platform {
    Logger.d("Creating Android platform instance with Koin-injected context", "Platform")
    return AndroidPlatform(
        deviceUDID = getDeviceUDIDWithContext(context),
        fcmKey = "", // This should be set from Firebase configuration when available
        appVersion = BuildConfig.VERSION_NAME,
        deviceOS = "Android",
        deviceOSVersion = Build.VERSION.RELEASE,
        deviceManufacturer = Build.MANUFACTURER,
        deviceModel = Build.MODEL
    )
}

@SuppressLint("HardwareIds")
private fun getDeviceUDIDWithContext(context: Context): String {
    return try {
        // Get the real Android ID using Koin-injected context
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: UUID.randomUUID().toString()
    } catch (exception: Exception) {
        Logger.w("Failed to get Android ID: ${exception.message}, using random UUID", "Platform")
        UUID.randomUUID().toString()
    }
}
