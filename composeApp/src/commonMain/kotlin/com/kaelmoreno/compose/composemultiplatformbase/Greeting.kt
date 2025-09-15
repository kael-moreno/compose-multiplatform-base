package com.kaelmoreno.compose.composemultiplatformbase

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}