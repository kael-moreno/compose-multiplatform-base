package com.kaelmoreno.compose.composemultiplatformbase.data.network

object HttpConstants {

    const val BASE_URL = "https://jsonplaceholder.typicode.com"

    object Endpoints {
        // User endpoints
        const val USERS = "$BASE_URL/users"
        const val USER_BY_ID = "$BASE_URL/users/{id}"

        // Posts endpoints (example of new endpoint)
        const val POSTS = "$BASE_URL/posts"
        const val POST_BY_ID = "$BASE_URL/posts/{id}"
        const val POSTS_BY_USER = "$BASE_URL/posts?userId={userId}"

        // Comments endpoints (example of another new endpoint)
        const val COMMENTS = "$BASE_URL/comments"
        const val COMMENT_BY_ID = "$BASE_URL/comments/{id}"
        const val COMMENTS_BY_POST = "$BASE_URL/comments?postId={postId}"

        // Albums endpoints (example of another new endpoint)
        const val ALBUMS = "$BASE_URL/albums"
        const val ALBUM_BY_ID = "$BASE_URL/albums/{id}"
        const val ALBUMS_BY_USER = "$BASE_URL/albums?userId={userId}"

        // Photos endpoints (example of another new endpoint)
        const val PHOTOS = "$BASE_URL/photos"
        const val PHOTO_BY_ID = "$BASE_URL/photos/{id}"
        const val PHOTOS_BY_ALBUM = "$BASE_URL/photos?albumId={albumId}"

        // Todos endpoints (example of another new endpoint)
        const val TODOS = "$BASE_URL/todos"
        const val TODO_BY_ID = "$BASE_URL/todos/{id}"
        const val TODOS_BY_USER = "$BASE_URL/todos?userId={userId}"
    }
}