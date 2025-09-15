package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen

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
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Create ViewModel in the composable
    val viewModel: UserViewModel = viewModel { UserViewModel() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Get loading and error states from BaseViewModel
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val successMessage by viewModel.successMessage.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Logger.i("UserListScreen initialized", "UI")
        viewModel.loadUsers()
    }

    // Clear success message after showing it
    LaunchedEffect(successMessage) {
        if (successMessage != null) {
            kotlinx.coroutines.delay(2000) // Show for 2 seconds
            viewModel.clearSuccessMessage()
        }
    }

    // Debug logging for UI state changes
    LaunchedEffect(uiState.selectedUser) {
        Logger.d("UI State selectedUser changed to: ${uiState.selectedUser?.name}", "UI")
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // TopAppBar with proper Material Icons
        TopAppBar(
            title = {
                Text("Users")
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
                IconButton(onClick = { viewModel.loadUsers() }) {
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
            // Success message snackbar
            successMessage?.let { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = message,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            when {
                isLoading -> {
                    LoadingContent()
                }
                error != null -> {
                    ErrorContent(
                        error = error!!,
                        onRetry = { viewModel.retry() }
                    )
                }
                uiState.users.isEmpty() -> {
                    EmptyContent(onRefresh = { viewModel.loadUsers() })
                }
                else -> {
                    // Put everything in a single LazyColumn for proper scrolling
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Header with user count
                        item {
                            Text(
                                text = "${uiState.users.size} users loaded",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        // User list items with details shown below each clicked item
                        items(uiState.users) { user ->
                            UserListItem(
                                user = user,
                                onClick = {
                                    if (uiState.selectedUser?.id == user.id) {
                                        viewModel.clearSelectedUser() // Close if same user clicked
                                    } else {
                                        viewModel.selectUser(user) // Select new user
                                    }
                                }
                            )

                            // Show details immediately below this user card if it's selected
                            if (uiState.selectedUser?.id == user.id) {
                                UserDetailCard(
                                    user = user,
                                    onDismiss = { viewModel.clearSelectedUser() }
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
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading users...")
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun EmptyContent(onRefresh: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No users found")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRefresh) {
                Text("Refresh")
            }
        }
    }
}

@Composable
private fun UserListItem(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            Logger.d("User card clicked: ${user.name}", "UI")
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun UserDetailCard(
    user: User,
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
                    text = "User Details",
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
                text = "Name: ${user.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Username: @${user.username}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Email: ${user.email}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Phone: ${user.phone}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Website: ${user.website}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Company: ${user.company.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Address: ${user.address.street}, ${user.address.city}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
