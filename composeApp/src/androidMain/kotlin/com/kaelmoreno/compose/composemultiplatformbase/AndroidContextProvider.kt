package com.kaelmoreno.compose.composemultiplatformbase

import android.content.Context
import java.lang.ref.WeakReference

/**
 * Singleton to provide Android Context throughout the app.
 * This allows us to access Context from platform-specific implementations
 * without needing to pass it through the common interface.
 * Uses WeakReference to prevent memory leaks.
 */
object AndroidContextProvider {
    private var contextRef: WeakReference<Context>? = null

    /**
     * Initialize the context provider with the application context.
     * This should be called once from MainActivity.
     */
    fun initialize(context: Context) {
        this.contextRef = WeakReference(context.applicationContext)
        Logger.d("AndroidContextProvider initialized", "AndroidContextProvider")
    }

    /**
     * Get the application context.
     * Returns null if not initialized or if context was garbage collected.
     */
    fun getContext(): Context? = contextRef?.get()

    /**
     * Check if the context provider has been initialized and context is still available.
     */
    fun isInitialized(): Boolean = contextRef?.get() != null
}
