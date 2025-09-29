package com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.network.ResponseHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base ViewModel that provides common loading and error state management
 * that can be reused across different ViewModels
 */
abstract class BaseViewModel<SideEffect : BaseSideEffect> : ViewModel() {

    // Global loading state - can be used for any operation
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Global error state - can be used for any operation
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Success state - can be used to show success messages
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

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
                _error.update { null }

                operation().collect { response ->
                    when (response) {
                        is ResponseHandler.Loading -> {
                            if (showLoading) {
                                Logger.d("Operation loading", logTag)
                                _isLoading.update { true }
                            }
                        }
                        is ResponseHandler.Success -> {
                            if (showLoading) {
                                _isLoading.update { false }
                            }
                            Logger.d("Operation completed successfully", logTag)
                            onSuccess(response.result!!)
                        }
                        is ResponseHandler.Error -> {
                            if (showLoading) {
                                _isLoading.update { false }
                            }
                            val errorMessage = response.apiError?.error?.message ?: "API Error"
                            Logger.e("Operation failed: $errorMessage", tag = logTag)
                            _error.update { errorMessage }
                            onError?.invoke(errorMessage) ?: run {
                                Logger.d("No custom error handler provided, using default error state", logTag)
                            }
                        }
                        is ResponseHandler.Failure -> {
                            if (showLoading) {
                                _isLoading.update { false }
                            }
                            val errorMessage = response.exception?.message ?: "Unknown error occurred"
                            Logger.e("Operation failed with exception: $errorMessage", response.exception, logTag)
                            _error.update { errorMessage }
                            onError?.invoke(errorMessage) ?: run {
                                Logger.d("No custom error handler provided, using default error state", logTag)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                if (showLoading) {
                    _isLoading.update { false }
                }
                val errorMessage = e.message ?: "Unknown error occurred"
                Logger.e("Flow operation failed with exception: $errorMessage", e, logTag)
                _error.update { errorMessage }
                onError?.invoke(errorMessage)
            }
        }
    }

    /**
     * Execute a simple suspend operation with automatic loading and error handling
     * @param operation The suspend function to execute
     * @param onSuccess Callback for successful result
     * @param onError Optional callback for error handling
     * @param showLoading Whether to show loading state (default: true)
     * @param logTag Tag for logging (default: class name)
     */
    protected fun <T> executeOperation(
        operation: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
        showLoading: Boolean = true,
        logTag: String = this::class.simpleName ?: "BaseViewModel"
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) {
                    _isLoading.update { true }
                }
                _error.update { null }

                Logger.d("Starting operation", logTag)
                val result = operation()

                if (showLoading) {
                    _isLoading.update { false }
                }

                Logger.d("Operation completed successfully", logTag)
                onSuccess(result)
            } catch (e: Exception) {
                if (showLoading) {
                    _isLoading.update { false }
                }
                val errorMessage = e.message ?: "Unknown error occurred"
                Logger.e("Operation failed: $errorMessage", e, tag = logTag)
                _error.update { errorMessage }
                onError?.invoke(errorMessage)
            }
        }
    }

    /**
     * Clear the current error
     */
    fun clearError() {
        Logger.d("Clearing error", this::class.simpleName ?: "BaseViewModel")
        _error.update { null }
    }

    /**
     * Clear the current success message
     */
    fun clearSuccessMessage() {
        Logger.d("Clearing success message", this::class.simpleName ?: "BaseViewModel")
        _successMessage.update { null }
    }

    /**
     * Set a success message
     */
    protected fun setSuccessMessage(message: String) {
        Logger.d("Setting success message: $message", this::class.simpleName ?: "BaseViewModel")
        _successMessage.update { message }
    }

    /**
     * Set loading state manually if needed
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.update { loading }
    }

    /**
     * Set error state manually if needed
     */
    protected fun setError(error: String?) {
        _error.update { error }
    }

    protected fun sendSideEffect(effect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

    /**
     * Retry operation - to be implemented by child ViewModels
     */
    abstract fun retry()
}