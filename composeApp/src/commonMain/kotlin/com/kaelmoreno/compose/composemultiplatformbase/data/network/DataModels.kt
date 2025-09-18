package com.kaelmoreno.compose.composemultiplatformbase.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataWrapper<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("api_key")
    val apiKey: String? = null
)

