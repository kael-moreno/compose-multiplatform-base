package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.data.model.Post
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.EmptyContent
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.ErrorContent
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.LoadingContent
import com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel.PostsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsListScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Use Koin for ViewModel injection
    val viewModel: PostsViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Get loading and error states from BaseViewModel
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Logger.i("PostsListScreen initialized", "UI")
        viewModel.loadPosts()
    }

    Column(
        modifier = modifier.fillMaxSize()
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
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                // Refresh action in app bar
                IconButton(onClick = { viewModel.loadPosts() }) {
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
            when {
                isLoading -> {
                    LoadingContent(
                        itemLoading = "Posts"
                    )
                }
                error != null -> {
                    ErrorContent(
                        error = error!!,
                        onRetry = { viewModel.retry() }
                    )
                }
                uiState.posts.isEmpty() -> {
                    EmptyContent(onRefresh = { viewModel.loadPosts() })
                }
                else -> {
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
                                        viewModel.clearSelectedPost() // Close if same post clicked
                                    } else {
                                        viewModel.selectPost(post) // Select new post
                                    }
                                }
                            )

                            // Show details immediately below this post card if it's selected
                            if (uiState.selectedPost?.id == post.id) {
                                PostDetailCard(
                                    post = post,
                                    onDismiss = { viewModel.clearSelectedPost() }
                                )
                            }
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
