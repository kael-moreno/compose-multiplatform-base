package com.kaelmoreno.compose.composemultiplatformbase.data.network

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.Platform
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import com.kaelmoreno.compose.composemultiplatformbase.data.repository.EncryptedDataStoreRepository
import kotlinx.coroutines.flow.Flow

class ApiService(
    private val platform: Platform,
    private val encryptedDataStoreRepository: EncryptedDataStoreRepository
) {

    // User endpoints
    fun getUsers(): Flow<ResponseHandler<List<User>>> {
        Logger.d("Fetching users from API", "ApiService")

        return enqueue<List<User>>(
            platform = platform,
            encryptedDataStoreRepository = encryptedDataStoreRepository,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.USERS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} users", "ApiService")
                result
            }
        )
    }

    // Post endpoints
    fun getPosts(): Flow<ResponseHandler<List<Post>>> {
        Logger.d("Fetching posts from API", "ApiService")

        return enqueue<List<Post>>(
            platform = platform,
            encryptedDataStoreRepository = encryptedDataStoreRepository,
            httpMethod = HttpMethods.GET,
            httpEndpoint = HttpConstants.Endpoints.POSTS,
            onSuccessResult = { _, result ->
                Logger.i("Successfully fetched ${result.size} posts", "ApiService")
                result
            }
        )
    }
}
