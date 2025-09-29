package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen.user_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.kaelmoreno.compose.composemultiplatformbase.data.model.User
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.EmptyContent
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.ErrorContent
import com.kaelmoreno.compose.composemultiplatformbase.presentation.defaults.LoadingContent
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun UserListScreenRoot(
    onBack: () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Get loading and error states from BaseViewModel
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Logger.i("UserListScreen initialized", "UI")
        viewModel.onAction(UserListScreenAction.OnLoadUsers)
    }

    // Debug logging for UI state changes
    LaunchedEffect(uiState.selectedUser) {
        Logger.d("UI State selectedUser changed to: ${uiState.selectedUser?.name}", "UI")
    }


    UserListScreen(
        isLoading,
        error,
        uiState
    ) { action ->
        viewModel.onAction(action)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    isLoading: Boolean,
    error: String?,
    uiState: UserUiState,
    action: (UserListScreenAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TopAppBar with proper Material Icons
        TopAppBar(
            title = {
                Text("Users")
            },
            navigationIcon = {
                IconButton(onClick = {
                    Logger.d("Back button pressed", "UI")
                    action(UserListScreenAction.OnBackNavigation)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                // Refresh action in app bar
                IconButton(onClick = { action(UserListScreenAction.OnLoadUsers) }) {
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
                        itemLoading = "Users"
                    )
                }

                error != null -> {
                    ErrorContent(
                        error = error,
                        onRetry = { action(UserListScreenAction.OnRetry) }
                    )
                }

                uiState.users.isEmpty() -> {
                    EmptyContent(onRefresh = { action(UserListScreenAction.OnLoadUsers) })
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
                                        action(UserListScreenAction.OnClearSelectedUser) // Close if same user clicked
                                    } else {
                                        action(UserListScreenAction.OnSelectUser(user)) // Select new user
                                    }
                                }
                            )

                            // Show details immediately below this user card if it's selected
                            if (uiState.selectedUser?.id == user.id) {
                                UserDetailCard(
                                    user = user,
                                    onDismiss = { action(UserListScreenAction.OnClearSelectedUser) }
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
                text = user.email!!,
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
                text = "Company: ${user.company!!.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Address: ${user.address!!.street}, ${user.address.city}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
