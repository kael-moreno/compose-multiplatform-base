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
            val response = client.get("$baseUrl/users") {
                // Add headers to help with content-length issues
                headers {
                    append("Accept", "application/json")
                    append("User-Agent", "KMM-App/1.0")
                }
            }
            val users: List<User> = response.body()
            Logger.i("Successfully fetched ${users.size} users", "UserApiService")
            Result.success(users)
        } catch (e: Exception) {
            Logger.e("Error fetching users", e, "UserApiService")

            // Retry once if it's a content-length mismatch on iOS
            if (e.message?.contains("Content-Length mismatch") == true) {
                Logger.w("Retrying due to content-length mismatch", "UserApiService")
                try {
                    val retryResponse = client.get("$baseUrl/users") {
                        headers {
                            append("Accept", "application/json")
                            append("User-Agent", "KMM-App/1.0")
                            append("Connection", "close") // Force connection close
                        }
                    }
                    val retryUsers: List<User> = retryResponse.body()
                    Logger.i("Retry successful: fetched ${retryUsers.size} users", "UserApiService")
                    Result.success(retryUsers)
                } catch (retryException: Exception) {
                    Logger.e("Retry also failed", retryException, "UserApiService")
                    Result.failure(retryException)
                }
            } else {
                Result.failure(e)
            }
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
