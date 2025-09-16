package com.kaelmoreno.compose.composemultiplatformbase

import android.os.Build
import java.util.UUID

class AndroidPlatform(
    override val deviceUDID: String,
    override val fcmKey: String,
    override val appVersion: String,
    override val deviceOS: String,
    override val deviceOSVersion: String,
    override val deviceManufacturer: String,
    override val deviceModel: String,
    override var apiKey: String?
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform {
    Logger.d("Creating Android platform instance", "Platform")
    return AndroidPlatform(
        deviceUDID = UUID.randomUUID().toString(), // Generate a random UUID for now
        fcmKey = "", // This should be set from Firebase configuration
        appVersion = "1.0.0", // This should come from BuildConfig
        deviceOS = "Android",
        deviceOSVersion = Build.VERSION.RELEASE,
        deviceManufacturer = Build.MANUFACTURER,
        deviceModel = Build.MODEL,
        apiKey = null // Will be set after authentication
    )
}
