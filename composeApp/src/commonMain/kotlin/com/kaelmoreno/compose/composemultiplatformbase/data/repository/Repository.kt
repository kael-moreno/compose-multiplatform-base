package com.kaelmoreno.compose.composemultiplatformbase.data.repository

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.getPlatform
import com.kaelmoreno.compose.composemultiplatformbase.data.model.*
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ApiService
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ResponseHandler
import kotlinx.coroutines.flow.Flow

class Repository {

    private val apiService = ApiService()

    // Platform instance for direct network calls
    private val platform = getPlatform()

    // User Methods
    fun fetchUsers(): Flow<ResponseHandler<List<User>>> {
        Logger.i("Starting to fetch users", "Repository")
        return apiService.getUsers()
    }

    // Posts Methods
    fun fetchPosts(): Flow<ResponseHandler<List<Post>>> {
        Logger.i("Starting to fetch posts", "Repository")
        return apiService.getPosts()
    }

    // Helper method to update API key after authentication
    fun updateApiKey(apiKey: String) {
        platform.apiKey = apiKey
        apiService.updateApiKey(apiKey)
    }
}
