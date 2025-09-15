package com.kaelmoreno.compose.composemultiplatformbase.data.network

import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import io.ktor.client.call.*
import io.ktor.client.request.*

class UserApiService {

    private val client = NetworkClient.httpClient
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getUsers(): Result<List<User>> {
        return try {
            Logger.i("Fetching users from API", "UserApiService")
            val response = client.get("$baseUrl/users")
            val users: List<User> = response.body()
            Logger.i("Successfully fetched ${users.size} users", "UserApiService")
            Result.success(users)
        } catch (e: Exception) {
            Logger.e("Error fetching users", e, "UserApiService")
            Result.failure(e)
        }
    }

    suspend fun getUserById(id: Int): Result<User> {
        return try {
            Logger.i("Fetching user with id: $id", "UserApiService")
            val response = client.get("$baseUrl/users/$id")
            val user: User = response.body()
            Logger.i("Successfully fetched user: ${user.name}", "UserApiService")
            Result.success(user)
        } catch (e: Exception) {
            Logger.e("Error fetching user with id: $id", e, "UserApiService")
            Result.failure(e)
        }
    }
}
