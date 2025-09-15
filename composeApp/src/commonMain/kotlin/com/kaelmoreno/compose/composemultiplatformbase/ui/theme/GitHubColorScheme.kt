package com.kaelmoreno.compose.composemultiplatformbase.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// GitHub-inspired color palette
object GitHubColors {
    // Grays and blacks
    val Gray900 = Color(0xFF0D1117) // GitHub dark background
    val Gray800 = Color(0xFF161B22) // GitHub dark card background
    val Gray700 = Color(0xFF21262D) // GitHub dark elevated surface
    val Gray600 = Color(0xFF30363D) // GitHub dark border
    val Gray500 = Color(0xFF484F58) // GitHub medium gray
    val Gray400 = Color(0xFF656D76) // GitHub light gray text
    val Gray300 = Color(0xFF8B949E) // GitHub muted text
    val Gray200 = Color(0xFFB1BAC4) // GitHub light muted
    val Gray100 = Color(0xFFD0D7DE) // GitHub light border
    val Gray50 = Color(0xFFF6F8FA)  // GitHub light background

    // Accent colors
    val Blue = Color(0xFF0969DA)    // GitHub blue
    val BlueLight = Color(0xFF54AEFF) // Light blue
    val Green = Color(0xFF1F883D)   // GitHub green
    val Red = Color(0xFFD1242F)     // GitHub red
    val Orange = Color(0xFFFB8500)  // GitHub orange
    val Purple = Color(0xFF8250DF)  // GitHub purple

    // Pure colors
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
}

// Light theme - GitHub light mode
val GitHubLightColorScheme = lightColorScheme(
    primary = GitHubColors.Blue,
    onPrimary = GitHubColors.White,
    primaryContainer = GitHubColors.BlueLight.copy(alpha = 0.1f),
    onPrimaryContainer = GitHubColors.Blue,

    secondary = GitHubColors.Gray600,
    onSecondary = GitHubColors.White,
    secondaryContainer = GitHubColors.Gray100,
    onSecondaryContainer = GitHubColors.Gray700,

    tertiary = GitHubColors.Purple,
    onTertiary = GitHubColors.White,
    tertiaryContainer = GitHubColors.Purple.copy(alpha = 0.1f),
    onTertiaryContainer = GitHubColors.Purple,

    error = GitHubColors.Red,
    onError = GitHubColors.White,
    errorContainer = GitHubColors.Red.copy(alpha = 0.1f),
    onErrorContainer = GitHubColors.Red,

    background = GitHubColors.Gray50,
    onBackground = GitHubColors.Gray800,

    surface = GitHubColors.White,
    onSurface = GitHubColors.Gray800,
    surfaceVariant = GitHubColors.Gray100,
    onSurfaceVariant = GitHubColors.Gray600,

    outline = GitHubColors.Gray200,
    outlineVariant = GitHubColors.Gray100,

    scrim = GitHubColors.Black.copy(alpha = 0.5f)
)

// Dark theme - GitHub dark mode
val GitHubDarkColorScheme = darkColorScheme(
    primary = GitHubColors.BlueLight,
    onPrimary = GitHubColors.Gray900,
    primaryContainer = GitHubColors.Blue.copy(alpha = 0.2f),
    onPrimaryContainer = GitHubColors.BlueLight,

    secondary = GitHubColors.Gray400,
    onSecondary = GitHubColors.Gray900,
    secondaryContainer = GitHubColors.Gray700,
    onSecondaryContainer = GitHubColors.Gray200,

    tertiary = GitHubColors.Purple,
    onTertiary = GitHubColors.Gray900,
    tertiaryContainer = GitHubColors.Purple.copy(alpha = 0.2f),
    onTertiaryContainer = GitHubColors.Purple,

    error = GitHubColors.Red,
    onError = GitHubColors.White,
    errorContainer = GitHubColors.Red.copy(alpha = 0.2f),
    onErrorContainer = GitHubColors.Red,

    background = GitHubColors.Gray900,
    onBackground = GitHubColors.Gray200,

    surface = GitHubColors.Gray800,
    onSurface = GitHubColors.Gray200,
    surfaceVariant = GitHubColors.Gray700,
    onSurfaceVariant = GitHubColors.Gray300,

    outline = GitHubColors.Gray600,
    outlineVariant = GitHubColors.Gray700,

    scrim = GitHubColors.Black.copy(alpha = 0.7f)
)
