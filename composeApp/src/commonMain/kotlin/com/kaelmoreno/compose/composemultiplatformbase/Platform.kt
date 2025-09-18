package com.kaelmoreno.compose.composemultiplatformbase

interface Platform {
    val name: String
    val deviceUDID: String
    val fcmKey: String
    val appVersion: String
    val deviceOS: String
    val deviceOSVersion: String
    val deviceManufacturer: String
    val deviceModel: String
}
