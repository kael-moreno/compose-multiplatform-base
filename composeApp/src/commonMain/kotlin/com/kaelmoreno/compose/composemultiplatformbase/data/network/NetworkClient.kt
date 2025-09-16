package com.kaelmoreno.compose.composemultiplatformbase.data.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object NetworkClient {
    val httpClient = HttpClient {

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 60000
            socketTimeoutMillis = 60000
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    com.kaelmoreno.compose.composemultiplatformbase.Logger.d(message, "Ktor")
                }
            }
            level = LogLevel.INFO
        }

        expectSuccess = false
    }
}
