package com.kaelmoreno.compose.composemultiplatformbase.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaelmoreno.compose.composemultiplatformbase.Logger

@Composable
fun MainScreen(
    onNavigateToUsers: () -> Unit,
    onNavigateToPosts: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        Logger.i("MainScreen initialized", "UI")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
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
            text = "Demo App",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        // Navigation Buttons
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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

                // Users Button
                Button(
                    onClick = {
                        Logger.d("Navigating to Users screen", "MainScreen")
                        onNavigateToUsers()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("👤 View Users")
                }

                // Posts Button
                Button(
                    onClick = {
                        Logger.d("Navigating to Posts screen", "MainScreen")
                        onNavigateToPosts()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📄 View Posts")
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
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Built with Kotlin Multiplatform & Compose",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
