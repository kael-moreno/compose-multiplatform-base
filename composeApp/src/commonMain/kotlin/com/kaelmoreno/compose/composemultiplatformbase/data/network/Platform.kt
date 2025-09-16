package com.kaelmoreno.compose.composemultiplatformbase.data.network
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

interface Platform {
    val name: String
    val deviceUDID: String
    val fcmKey: String
    val appVersion: String
    val deviceOS: String
    val deviceOSVersion: String
    val deviceManufacturer: String
    val deviceModel: String
    val apiKey: String?
}

expect fun httpClient(platform: Platform, config: HttpClientConfig<*>.() -> Unit = {}): HttpClient