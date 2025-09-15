package com.kaelmoreno.compose.composemultiplatformbase.data.network

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService {

    private val client = NetworkClient.httpClient

    // User endpoints
    suspend fun getUsers(): Result<List<User>> {
        return try {
            Logger.d("Fetching users from API", "ApiService")
            val response = client.get(HttpConstants.Endpoints.USERS)

            if (response.status.isSuccess()) {
                val users = response.body<List<User>>()
                Logger.i("Successfully fetched ${users.size} users", "ApiService")
                Result.success(users)
            } else {
                Logger.e("Failed to fetch users: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching users", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: Int): Result<User> {
        return try {
            Logger.d("Fetching user with id: $id", "ApiService")
            val url = HttpConstants.Endpoints.USER_BY_ID.replace("{id}", id.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val user = response.body<User>()
                Logger.i("Successfully fetched user: ${user.name}", "ApiService")
                Result.success(user)
            } else {
                Logger.e("Failed to fetch user $id: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching user $id", e, "ApiService")
            Result.failure(e)
        }
    }

    // Post endpoints
    suspend fun getPosts(): Result<List<Post>> {
        return try {
            Logger.d("Fetching posts from API", "ApiService")
            val response = client.get(HttpConstants.Endpoints.POSTS)

            if (response.status.isSuccess()) {
                val posts = response.body<List<Post>>()
                Logger.i("Successfully fetched ${posts.size} posts", "ApiService")
                Result.success(posts)
            } else {
                Logger.e("Failed to fetch posts: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching posts", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getPostById(id: Int): Result<Post> {
        return try {
            Logger.d("Fetching post with id: $id", "ApiService")
            val url = HttpConstants.Endpoints.POST_BY_ID.replace("{id}", id.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val post = response.body<Post>()
                Logger.i("Successfully fetched post: ${post.title}", "ApiService")
                Result.success(post)
            } else {
                Logger.e("Failed to fetch post $id: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching post $id", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getPostsByUser(userId: Int): Result<List<Post>> {
        return try {
            Logger.d("Fetching posts for user: $userId", "ApiService")
            val url = HttpConstants.Endpoints.POSTS_BY_USER.replace("{userId}", userId.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val posts = response.body<List<Post>>()
                Logger.i("Successfully fetched ${posts.size} posts for user $userId", "ApiService")
                Result.success(posts)
            } else {
                Logger.e("Failed to fetch posts for user $userId: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching posts for user $userId", e, "ApiService")
            Result.failure(e)
        }
    }

    // Comment endpoints
    suspend fun getComments(): Result<List<Comment>> {
        return try {
            Logger.d("Fetching comments from API", "ApiService")
            val response = client.get(HttpConstants.Endpoints.COMMENTS)

            if (response.status.isSuccess()) {
                val comments = response.body<List<Comment>>()
                Logger.i("Successfully fetched ${comments.size} comments", "ApiService")
                Result.success(comments)
            } else {
                Logger.e("Failed to fetch comments: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching comments", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getCommentsByPost(postId: Int): Result<List<Comment>> {
        return try {
            Logger.d("Fetching comments for post: $postId", "ApiService")
            val url = HttpConstants.Endpoints.COMMENTS_BY_POST.replace("{postId}", postId.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val comments = response.body<List<Comment>>()
                Logger.i("Successfully fetched ${comments.size} comments for post $postId", "ApiService")
                Result.success(comments)
            } else {
                Logger.e("Failed to fetch comments for post $postId: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching comments for post $postId", e, "ApiService")
            Result.failure(e)
        }
    }

    // Album endpoints
    suspend fun getAlbums(): Result<List<Album>> {
        return try {
            Logger.d("Fetching albums from API", "ApiService")
            val response = client.get(HttpConstants.Endpoints.ALBUMS)

            if (response.status.isSuccess()) {
                val albums = response.body<List<Album>>()
                Logger.i("Successfully fetched ${albums.size} albums", "ApiService")
                Result.success(albums)
            } else {
                Logger.e("Failed to fetch albums: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching albums", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getAlbumsByUser(userId: Int): Result<List<Album>> {
        return try {
            Logger.d("Fetching albums for user: $userId", "ApiService")
            val url = HttpConstants.Endpoints.ALBUMS_BY_USER.replace("{userId}", userId.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val albums = response.body<List<Album>>()
                Logger.i("Successfully fetched ${albums.size} albums for user $userId", "ApiService")
                Result.success(albums)
            } else {
                Logger.e("Failed to fetch albums for user $userId: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching albums for user $userId", e, "ApiService")
            Result.failure(e)
        }
    }

    // Photo endpoints
    suspend fun getPhotosByAlbum(albumId: Int): Result<List<Photo>> {
        return try {
            Logger.d("Fetching photos for album: $albumId", "ApiService")
            val url = HttpConstants.Endpoints.PHOTOS_BY_ALBUM.replace("{albumId}", albumId.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val photos = response.body<List<Photo>>()
                Logger.i("Successfully fetched ${photos.size} photos for album $albumId", "ApiService")
                Result.success(photos)
            } else {
                Logger.e("Failed to fetch photos for album $albumId: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching photos for album $albumId", e, "ApiService")
            Result.failure(e)
        }
    }

    // Todo endpoints
    suspend fun getTodos(): Result<List<Todo>> {
        return try {
            Logger.d("Fetching todos from API", "ApiService")
            val response = client.get(HttpConstants.Endpoints.TODOS)

            if (response.status.isSuccess()) {
                val todos = response.body<List<Todo>>()
                Logger.i("Successfully fetched ${todos.size} todos", "ApiService")
                Result.success(todos)
            } else {
                Logger.e("Failed to fetch todos: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching todos", e, "ApiService")
            Result.failure(e)
        }
    }

    suspend fun getTodosByUser(userId: Int): Result<List<Todo>> {
        return try {
            Logger.d("Fetching todos for user: $userId", "ApiService")
            val url = HttpConstants.Endpoints.TODOS_BY_USER.replace("{userId}", userId.toString())
            val response = client.get(url)

            if (response.status.isSuccess()) {
                val todos = response.body<List<Todo>>()
                Logger.i("Successfully fetched ${todos.size} todos for user $userId", "ApiService")
                Result.success(todos)
            } else {
                Logger.e("Failed to fetch todos for user $userId: ${response.status}", null, "ApiService")
                Result.failure(Exception("HTTP ${response.status.value}: ${response.status.description}"))
            }
        } catch (e: Exception) {
            Logger.e("Error fetching todos for user $userId", e, "ApiService")
            Result.failure(e)
        }
    }
}
