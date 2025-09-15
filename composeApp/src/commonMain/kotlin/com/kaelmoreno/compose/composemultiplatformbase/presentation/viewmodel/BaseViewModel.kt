package com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
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
     * Execute an operation with automatic loading and error handling
     * @param operation The suspend function to execute
     * @param onSuccess Optional callback for successful completion
     * @param onError Optional callback for error handling
     * @param showLoading Whether to show loading state (default: true)
     * @param logTag Tag for logging (default: class name)
     */
    protected fun executeOperation(
        operation: suspend () -> Unit,
        onSuccess: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        showLoading: Boolean = true,
        logTag: String = this::class.simpleName ?: "BaseViewModel"
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) {
                    _isLoading.value = true
                }
                _error.value = null

                Logger.d("Starting operation", logTag)
                operation()
                Logger.d("Operation completed successfully", logTag)

                onSuccess?.invoke()

            } catch (e: Exception) {
                Logger.e("Operation failed", e, logTag)
                _error.value = e.message ?: "Unknown error occurred"
                onError?.invoke(e)
            } finally {
                if (showLoading) {
                    _isLoading.value = false
                }
            }
        }
    }

    /**
     * Execute an operation that returns a Result with automatic loading and error handling
     * @param operation The suspend function that returns Result<T>
     * @param onSuccess Callback for successful result
     * @param onError Optional callback for error handling
     * @param showLoading Whether to show loading state (default: true)
     * @param logTag Tag for logging (default: class name)
     */
    protected fun <T> executeOperationWithResult(
        operation: suspend () -> Result<T>,
        onSuccess: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        showLoading: Boolean = true,
        logTag: String = this::class.simpleName ?: "BaseViewModel"
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) {
                    _isLoading.value = true
                }
                _error.value = null

                Logger.d("Starting operation with result", logTag)
                val result = operation()

                result.onSuccess { data ->
                    Logger.d("Operation completed successfully", logTag)
                    onSuccess(data)
                }.onFailure { exception ->
                    Logger.e("Operation failed", exception, logTag)
                    _error.value = exception.message ?: "Unknown error occurred"
                    onError?.invoke(exception)
                }

            } catch (e: Exception) {
                Logger.e("Operation execution failed", e, logTag)
                _error.value = e.message ?: "Unknown error occurred"
                onError?.invoke(e)
            } finally {
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
