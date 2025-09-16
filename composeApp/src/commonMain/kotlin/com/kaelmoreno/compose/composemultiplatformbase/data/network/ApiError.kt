package com.kaelmoreno.compose.composemultiplatformbase.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiError(

    @SerialName("error")
    val error: Error,
    val httpUrl: String = ""

)

@Serializable
data class Error(
    @SerialName("http_code")
    val httpCode: Int,

    @SerialName("code")
    val code: String,

    @SerialName("message")
    val message: String
)


