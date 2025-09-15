package com.kaelmoreno.compose.composemultiplatformbase.data.network

object HttpConstants {

    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    object Endpoints {
        const val USERS = "${BASE_URL}/users"
        const val USER_BY_ID = "${BASE_URL}/users/{id}"
    }

}