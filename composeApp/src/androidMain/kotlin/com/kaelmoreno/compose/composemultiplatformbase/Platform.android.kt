package com.kaelmoreno.compose.composemultiplatformbase

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import java.util.UUID

class AndroidPlatform(
    override val deviceUDID: String,
    override val fcmKey: String,
    override val appVersion: String,
    override val deviceOS: String,
    override val deviceOSVersion: String,
    override val deviceManufacturer: String,
    override val deviceModel: String
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform {
    Logger.d("Creating Android platform instance", "Platform")
    return AndroidPlatform(
        deviceUDID = getDeviceUDID(),
        fcmKey = "", // This should be set from Firebase configuration when available
        appVersion = BuildConfig.VERSION_NAME,
        deviceOS = "Android",
        deviceOSVersion = Build.VERSION.RELEASE,
        deviceManufacturer = Build.MANUFACTURER,
        deviceModel = Build.MODEL
    )
}

// Overloaded function for when context is available
fun getPlatform(context: Context): Platform {
    Logger.d("Creating Android platform instance with context", "Platform")
    return AndroidPlatform(
        deviceUDID = getDeviceUDID(context),
        fcmKey = "", // This should be set from Firebase configuration when available
        appVersion = BuildConfig.VERSION_NAME,
        deviceOS = "Android",
        deviceOSVersion = Build.VERSION.RELEASE,
        deviceManufacturer = Build.MANUFACTURER,
        deviceModel = Build.MODEL
    )
}

private fun getDeviceUDID(): String {
    // Fallback when no context available
    return UUID.randomUUID().toString()
}

@SuppressLint("HardwareIds")
private fun getDeviceUDID(context: Context): String {
    return try {
        // Get the real Android ID
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: UUID.randomUUID().toString()
    } catch (e: Exception) {
        Logger.w("Failed to get Android ID, using random UUID", "Platform")
        UUID.randomUUID().toString()
    }
}
