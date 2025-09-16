package com.kaelmoreno.compose.composemultiplatformbase

import platform.UIKit.UIDevice
import platform.Foundation.NSUUID

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

actual fun getPlatform(): Platform {
    Logger.d("Creating iOS platform instance", "Platform")
    val device = UIDevice.currentDevice
    return IOSPlatform(
        deviceUDID = NSUUID().UUIDString(), // Generate a random UUID
        fcmKey = "", // This should be set from Firebase configuration
        appVersion = "1.0.0", // This should come from Info.plist
        deviceOS = device.systemName(),
        deviceOSVersion = device.systemVersion(),
        deviceManufacturer = "Apple",
        deviceModel = device.model()
    )
}
