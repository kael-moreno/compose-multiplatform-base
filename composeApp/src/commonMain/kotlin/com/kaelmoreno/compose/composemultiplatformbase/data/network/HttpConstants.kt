package com.kaelmoreno.compose.composemultiplatformbase.data.network

object HttpConstants {

    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    object Endpoints {
        // User endpoints
        const val USERS = "$BASE_URL/users"

        // Posts endpoints (example of new endpoint)
        const val POSTS = "$BASE_URL/posts"
    }
}