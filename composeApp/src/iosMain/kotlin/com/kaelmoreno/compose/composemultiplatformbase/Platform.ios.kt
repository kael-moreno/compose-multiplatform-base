package com.kaelmoreno.compose.composemultiplatformbase

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform {
    Logger.d("Creating iOS platform instance", "Platform")
    return IOSPlatform()
}
