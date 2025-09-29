package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.post_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.BaseContent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostListScreenRoot(
    onBack: () -> Unit,
    viewModel: PostsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Get loading and error states from BaseViewModel
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Logger.i("PostsListScreen initialized", "UI")
        viewModel.onAction(PostListScreenAction.OnLoadPosts)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { sideEffect ->
            when (sideEffect) {
                PostListScreenSideEffect.BackNavigate -> {
                    Logger.d("Back navigation side effect received", "UI")
                    onBack()
                }
            }
        }
    }

    PostsListScreen(
        isLoading,
        error,
        uiState
    ) { action ->
        viewModel.onAction(action)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsListScreen(
    isLoading: Boolean,
    error: String?,
    uiState: PostsUiState,
    action: (PostListScreenAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TopAppBar with proper Material Icons
        TopAppBar(
            title = {
                Text("Posts")
            },
            navigationIcon = {
                IconButton(onClick = {
                    Logger.d("Back button pressed", "UI")
                    action(PostListScreenAction.OnBackNavigate)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                // Refresh action in app bar
                IconButton(onClick = { action(PostListScreenAction.OnLoadPosts) }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        )

        // Content with padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Using the new BaseContent composable
            BaseContent(
                isLoading = isLoading,
                error = error,
                items = uiState.posts,
                itemName = "Posts",
                onRetry = { action(PostListScreenAction.OnRetry) },
                onRefresh = { action(PostListScreenAction.OnLoadPosts) }
            ) {
                // Content for non-loading, non-error, non-empty state
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    // Header with post count
                    item {
                        Text(
                            text = "${uiState.posts.size} posts loaded",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Posts list items with details shown below each clicked item
                    items(uiState.posts) { post ->
                        PostListItem(
                            post = post,
                            onClick = {
                                if (uiState.selectedPost?.id == post.id) {
                                    action(PostListScreenAction.OnClearSelectedPost) // Close if same post clicked
                                } else {
                                    action(PostListScreenAction.OnSelectPost(post))  // Select new post
                                }
                            }
                        )

                        // Show details immediately below this post card if it's selected
                        if (uiState.selectedPost?.id == post.id) {
                            PostDetailCard(
                                post = post,
                                onDismiss = { action(PostListScreenAction.OnClearSelectedPost) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PostListItem(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            Logger.d("Post card clicked: ${post.title}", "UI")
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Post ID: ${post.id} • User ID: ${post.userId}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PostDetailCard(
    post: Post,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Post Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness,
                color = DividerDefaults.color
            )

            Text(
                text = "Title:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Content:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Post ID: ${post.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "User ID: ${post.userId}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
