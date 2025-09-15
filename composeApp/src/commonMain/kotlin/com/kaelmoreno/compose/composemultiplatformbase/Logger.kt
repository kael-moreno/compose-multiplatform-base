package com.kaelmoreno.compose.composemultiplatformbase

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

/**
 * Multiplatform logger utility using Napier
 */
object Logger {

    fun initialize() {
        // Initialize Napier with DebugAntilog for debug builds
        Napier.base(DebugAntilog())
    }

    fun d(message: String, tag: String? = null) {
        Napier.d(message, tag = tag)
    }

    fun i(message: String, tag: String? = null) {
        Napier.i(message, tag = tag)
    }

    fun w(message: String, tag: String? = null) {
        Napier.w(message, tag = tag)
    }

    fun e(message: String, throwable: Throwable? = null, tag: String? = null) {
        Napier.e(message, throwable, tag = tag)
    }

    fun v(message: String, tag: String? = null) {
        Napier.v(message, tag = tag)
    }
}
