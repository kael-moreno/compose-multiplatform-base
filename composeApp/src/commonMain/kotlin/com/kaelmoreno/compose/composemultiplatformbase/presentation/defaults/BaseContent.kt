package com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A base content composable that handles common UI states like loading, error, and empty data.
 * This helps reduce boilerplate code in screens that follow the same state handling pattern.
 *
 * @param isLoading Whether the content is in loading state
 * @param error The error message if any, null if no error
 * @param isEmpty Whether the content is empty (after loading)
 * @param itemName The name of the items being loaded (e.g., "Posts", "Users")
 * @param onRetry Callback to retry loading after an error
 * @param onRefresh Callback to refresh the content when empty
 * @param emptyContent Optional custom empty content composable
 * @param content The content to display when not in loading, error, or empty state
 */
@Composable
fun BaseContent(
    isLoading: Boolean,
    error: String?,
    isEmpty: Boolean,
    itemName: String,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    when {
        isLoading -> {
            LoadingContent(itemLoading = itemName)
        }
        error != null -> {
            ErrorContent(error = error, onRetry = onRetry)
        }
        isEmpty -> {
            emptyContent?.invoke() ?: EmptyContent(onRefresh = onRefresh)
        }
        else -> {
            content()
        }
    }
}

/**
 * An overload of BaseContent that takes a collection to determine if it's empty.
 */
@Composable
fun <T> BaseContent(
    isLoading: Boolean,
    error: String?,
    items: Collection<T>,
    itemName: String,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    BaseContent(
        isLoading = isLoading,
        error = error,
        isEmpty = items.isEmpty(),
        itemName = itemName,
        onRetry = onRetry,
        onRefresh = onRefresh,
        modifier = modifier,
        emptyContent = emptyContent,
        content = content
    )
}
