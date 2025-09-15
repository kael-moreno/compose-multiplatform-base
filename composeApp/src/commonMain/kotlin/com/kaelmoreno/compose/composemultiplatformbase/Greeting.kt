package com.kaelmoreno.compose.composemultiplatformbase

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        Logger.d("Getting platform information", "Greeting")
        val greeting = "Hello, ${platform.name}!"
        Logger.i("Generated greeting: $greeting for platform: ${platform.name}", "Greeting")
        return greeting
    }
}