package com.kaelmoreno.compose.composemultiplatformbase.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val username: String,
    val theme: String,
    val notificationsEnabled: Boolean,
    val language: String
)
