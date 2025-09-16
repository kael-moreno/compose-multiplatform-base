package com.kaelmoreno.compose.composemultiplatformbase.data.network

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.getPlatform
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import kotlinx.coroutines.flow.Flow

class ApiService {

    private val platform = getPlatform()

    // User endpoints
    fun getUsers(): Flow<ResponseHandler<List<User>>> {
        Logger.d("Fetching users from API", "ApiService")

        return enqueue<List<User>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.USERS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} users", "ApiService")
                result
            }
        )
    }

    fun getUserById(id: Int): Flow<ResponseHandler<User>> {
        Logger.d("Fetching user with id: $id", "ApiService")
        val url = HttpConstants.Endpoints.USER_BY_ID.replace("{id}", id.toString())

        return enqueue<User>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = url,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched user: ${result.name}", "ApiService")
                result
            }
        )
    }

    // Post endpoints
    fun getPosts(): Flow<ResponseHandler<List<Post>>> {
        Logger.d("Fetching posts from API", "ApiService")

        return enqueue<List<Post>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.POSTS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} posts", "ApiService")
                result
            }
        )
    }

    fun getPostById(id: Int): Flow<ResponseHandler<Post>> {
        Logger.d("Fetching post with id: $id", "ApiService")
        val url = HttpConstants.Endpoints.POST_BY_ID.replace("{id}", id.toString())

        return enqueue<Post>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = url,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched post: ${result.title}", "ApiService")
                result
            }
        )
    }

    fun getPostsByUser(userId: Int): Flow<ResponseHandler<List<Post>>> {
        Logger.d("Fetching posts for user: $userId", "ApiService")

        return enqueue<List<Post>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.POSTS,
            query = arrayOf("userId" to userId.toString()),
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} posts for user $userId", "ApiService")
                result
            }
        )
    }

    // Comment endpoints
    fun getComments(): Flow<ResponseHandler<List<Comment>>> {
        Logger.d("Fetching comments from API", "ApiService")

        return enqueue<List<Comment>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.COMMENTS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} comments", "ApiService")
                result
            }
        )
    }

    fun getCommentsByPost(postId: Int): Flow<ResponseHandler<List<Comment>>> {
        Logger.d("Fetching comments for post: $postId", "ApiService")

        return enqueue<List<Comment>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.COMMENTS,
            query = arrayOf("postId" to postId.toString()),
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} comments for post $postId", "ApiService")
                result
            }
        )
    }

    // Album endpoints
    fun getAlbums(): Flow<ResponseHandler<List<Album>>> {
        Logger.d("Fetching albums from API", "ApiService")

        return enqueue<List<Album>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.ALBUMS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} albums", "ApiService")
                result
            }
        )
    }

    fun getAlbumsByUser(userId: Int): Flow<ResponseHandler<List<Album>>> {
        Logger.d("Fetching albums for user: $userId", "ApiService")

        return enqueue<List<Album>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.ALBUMS,
            query = arrayOf("userId" to userId.toString()),
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} albums for user $userId", "ApiService")
                result
            }
        )
    }

    // Photo endpoints
    fun getPhotosByAlbum(albumId: Int): Flow<ResponseHandler<List<Photo>>> {
        Logger.d("Fetching photos for album: $albumId", "ApiService")

        return enqueue<List<Photo>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.PHOTOS,
            query = arrayOf("albumId" to albumId.toString()),
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} photos for album $albumId", "ApiService")
                result
            }
        )
    }

    // Todo endpoints
    fun getTodos(): Flow<ResponseHandler<List<Todo>>> {
        Logger.d("Fetching todos from API", "ApiService")

        return enqueue<List<Todo>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.TODOS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} todos", "ApiService")
                result
            }
        )
    }

    fun getTodosByUser(userId: Int): Flow<ResponseHandler<List<Todo>>> {
        Logger.d("Fetching todos for user: $userId", "ApiService")

        return enqueue<List<Todo>>(
            platform = platform,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.TODOS,
            query = arrayOf("userId" to userId.toString()),
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} todos for user $userId", "ApiService")
                result
            }
        )
    }
}
