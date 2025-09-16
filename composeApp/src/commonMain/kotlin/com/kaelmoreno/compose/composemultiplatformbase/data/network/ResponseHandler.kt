package com.kaelmoreno.compose.composemultiplatformbase.data.network

sealed class ResponseHandler<out T>(
    val result: T? = null,
    val apiError: ApiError? = null,
    val exception: Throwable? = null
) {
    class Loading<T> : ResponseHandler<T>()
    class Success<T>(data: T) : ResponseHandler<T>(result = data)
    class Error<T>(apiError: ApiError) : ResponseHandler<T>(apiError = apiError)
    class Failure<T>(exception: Throwable) : ResponseHandler<T>(exception = exception)
}