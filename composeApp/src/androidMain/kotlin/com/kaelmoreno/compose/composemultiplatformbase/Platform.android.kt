package com.kaelmoreno.compose.composemultiplatformbase

import android.os.Build

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
