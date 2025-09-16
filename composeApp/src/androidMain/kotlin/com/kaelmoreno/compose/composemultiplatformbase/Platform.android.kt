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

private fun getDeviceUDID(): String {
    // Try to get context from AndroidContextProvider first
    val context = AndroidContextProvider.getContext()
    return if (context != null) {
        getDeviceUDID(context)
    } else {
        Logger.w("AndroidContextProvider not initialized, using random UUID fallback", "Platform")
        UUID.randomUUID().toString()
    }
}

@SuppressLint("HardwareIds")
private fun getDeviceUDID(context: Context): String {
    return try {
        // Get the real Android ID
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: UUID.randomUUID().toString()
    } catch (exception: Exception) {
        Logger.w("Failed to get Android ID: ${exception.message}, using random UUID", "Platform")
        UUID.randomUUID().toString()
    }
}
