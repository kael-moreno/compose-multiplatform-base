package com.kaelmoreno.compose.composemultiplatformbase.data.network

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import io.ktor.client.call.*
import io.ktor.client.request.*

class UserApiService {

    private val client = NetworkClient.httpClient

    suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = client.get(HttpConstants.Endpoints.USERS)
            val users: List<User> = response.body()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: Int): Result<User> {
        return try {
            val response = client.get(HttpConstants.Endpoints.USER_BY_ID.replace("{id}", id.toString()))
            val user: User = response.body()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
