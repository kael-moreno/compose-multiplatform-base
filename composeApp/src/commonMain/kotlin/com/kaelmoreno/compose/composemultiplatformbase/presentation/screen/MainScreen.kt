package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaelmoreno.compose.composemultiplatformbase.Logger
import com.kaelmoreno.compose.composemultiplatformbase.presentation.viewmodel.MainScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    onNavigateToUsers: () -> Unit,
    onNavigateToPosts: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        Logger.i("MainScreen initialized", "UI")
    }

    val uiState by viewModel.uiState.collectAsState()

    // Show snackbar for messages
    uiState.message?.let { message ->
        LaunchedEffect(message) {
            viewModel.clearMessage()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Title
        Text(
            text = "Compose Multiplatform Base",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Demo App with KSafe Integration",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // KSafe Demo Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Secure Storage",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "KSafe Secure Storage Demo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                HorizontalDivider()

                // Stored Data Display
                if (uiState.savedToken != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Stored String (Token):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = uiState.savedToken ?: "None",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                if (uiState.savedPreferences != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Stored JSON (User Preferences):",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Username: ${uiState.savedPreferences?.username}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Theme: ${uiState.savedPreferences?.theme}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Notifications: ${uiState.savedPreferences?.notificationsEnabled}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Language: ${uiState.savedPreferences?.language}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.saveStringDemo() },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Save String")
                        }
                    }

                    Button(
                        onClick = { viewModel.saveJsonDemo() },
                        enabled = !uiState.isLoading,
                        modifier = Modifier.weight(1f)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Save JSON")
                        }
                    }
                }

                OutlinedButton(
                    onClick = { viewModel.clearAllData() },
                    enabled = !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear All",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clear All Stored Data")
                }

                // Status Message
                uiState.message?.let { message ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (message.contains("Error") || message.contains("Failed")) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                MaterialTheme.colorScheme.primaryContainer
                            }
                        )
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Navigation Buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Available Screens",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Users Button with Material Icon
                Button(
                    onClick = {
                        Logger.d("Navigating to Users screen", "MainScreen")
                        onNavigateToUsers()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Users",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("View Users")
                }

                // Posts Button with Material Icon
                Button(
                    onClick = {
                        Logger.d("Navigating to Posts screen", "MainScreen")
                        onNavigateToPosts()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Article,
                        contentDescription = "Posts",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("View Posts")
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )

                Text(
                    text = "More screens coming soon...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Footer
        Text(
            text = "Built with Kotlin Multiplatform & Compose + KSafe",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
