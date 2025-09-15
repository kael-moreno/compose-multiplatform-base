package com.kaelmoreno.compose.composemultiplatformbase.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes using sealed classes and Kotlinx Serialization
 */
@Serializable
sealed class Screen {
    @Serializable
    data object Main : Screen()

    @Serializable
    data object Users : Screen()

    @Serializable
    data object Posts : Screen()
}
