package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ResponseHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel that provides common loading and error state management
 * that can be reused across different ViewModels
 */
abstract class BaseViewModel : ViewModel() {

    // Global loading state - can be used for any operation
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Global error state - can be used for any operation
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Success state - can be used to show success messages
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    /**
     * Execute an operation that returns a Flow<ResponseHandler<T>> with automatic loading and error handling
     * @param operation The suspend function that returns Flow<ResponseHandler<T>>
     * @param onSuccess Callback for successful result
     * @param onError Optional callback for error handling
     * @param showLoading Whether to show loading state (default: true)
     * @param logTag Tag for logging (default: class name)
     */
    protected fun <T> executeOperationWithFlow(
        operation: suspend () -> Flow<ResponseHandler<T>>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
        showLoading: Boolean = true,
        logTag: String = this::class.simpleName ?: "BaseViewModel"
    ) {
        viewModelScope.launch {
            try {
                Logger.d("Starting flow operation", logTag)
                _error.value = null

                operation().collect { response ->
                    when (response) {
                        is ResponseHandler.Loading -> {
                            if (showLoading) {
                                _isLoading.value = true
                            }
                            Logger.d("Loading...", logTag)
                        }
                        is ResponseHandler.Success -> {
                            if (showLoading) {
                                _isLoading.value = false
                            }
                            response.result?.let { data ->
                                Logger.d("Flow operation completed successfully", logTag)
                                onSuccess(data)
                            }
                        }
                        is ResponseHandler.Error -> {
                            if (showLoading) {
                                _isLoading.value = false
                            }
                            val errorMessage = response.apiError?.error?.message ?: "API error occurred"
                            Logger.e("Flow operation failed: $errorMessage", null, logTag)
                            _error.value = errorMessage
                            onError?.invoke(errorMessage)
                        }
                        is ResponseHandler.Failure -> {
                            if (showLoading) {
                                _isLoading.value = false
                            }
                            val errorMessage = response.exception?.message ?: "Network error occurred"
                            Logger.e("Flow operation failed", response.exception, logTag)
                            _error.value = errorMessage
                            onError?.invoke(errorMessage)
                        }
                    }
                }

            } catch (e: Exception) {
                Logger.e("Flow operation execution failed", e, logTag)
                _error.value = e.message ?: "Unknown error occurred"
                onError?.invoke(e.message ?: "Unknown error occurred")
                if (showLoading) {
                    _isLoading.value = false
                }
            }
        }
    }

    /**
     * Clear the current error state
     */
    fun clearError() {
        Logger.d("Clearing error", this::class.simpleName ?: "BaseViewModel")
        _error.value = null
    }

    /**
     * Clear the current success message
     */
    fun clearSuccessMessage() {
        Logger.d("Clearing success message", this::class.simpleName ?: "BaseViewModel")
        _successMessage.value = null
    }

    /**
     * Set a success message
     */
    protected fun setSuccessMessage(message: String) {
        Logger.d("Setting success message: $message", this::class.simpleName ?: "BaseViewModel")
        _successMessage.value = message
    }

    /**
     * Set loading state manually if needed
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    /**
     * Set error state manually if needed
     */
    protected fun setError(error: String?) {
        _error.value = error
    }

    /**
     * Retry operation - to be implemented by child ViewModels
     */
    abstract fun retry()
}
