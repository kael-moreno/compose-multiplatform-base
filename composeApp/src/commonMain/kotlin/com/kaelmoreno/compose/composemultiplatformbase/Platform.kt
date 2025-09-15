package com.kaelmoreno.compose.composemultiplatformbase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform