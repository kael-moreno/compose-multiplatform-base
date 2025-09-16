package com.kaelmoreno.compose.composemultiplatformbase

import platform.UIKit.UIDevice
import platform.Foundation.NSUUID
import platform.Foundation.NSBundle

class IOSPlatform(
    override val deviceUDID: String,
    override val fcmKey: String,
    override val appVersion: String,
    override val deviceOS: String,
    override val deviceOSVersion: String,
    override val deviceManufacturer: String,
    override val deviceModel: String
) : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

// Helper function used by the iOS platform module
internal fun createIOSPlatform(): Platform {
    Logger.d("Creating iOS platform instance", "Platform")
    val device = UIDevice.currentDevice
    val bundle = NSBundle.mainBundle

    return IOSPlatform(
        deviceUDID = NSUUID().UUIDString(),
        fcmKey = "", // This should be set from Firebase configuration when available
        appVersion = getAppVersion(bundle),
        deviceOS = device.systemName(),
        deviceOSVersion = device.systemVersion(),
        deviceManufacturer = "Apple",
        deviceModel = device.model()
    )
}

private fun getAppVersion(bundle: NSBundle): String {
    return try {
        // Try to get CFBundleShortVersionString first (user-facing version)
        val shortVersion = bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
        val buildNumber = bundle.objectForInfoDictionaryKey("CFBundleVersion") as? String

        when {
            shortVersion != null && buildNumber != null -> "$shortVersion ($buildNumber)"
            shortVersion != null -> shortVersion
            buildNumber != null -> buildNumber
            else -> "1.0.0" // Fallback
        }
    } catch (e: Exception) {
        Logger.w("Failed to get app version from Info.plist, using fallback", "Platform")
        "1.0.0"
    }
}
