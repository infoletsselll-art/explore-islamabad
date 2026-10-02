package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = Color(0xFF10B981), // Vibrant Emerald Green
    onPrimary = Color(0xFFFFFFFF), // Crisp White Text on Buttons
    primaryContainer = Color(0xFF064E3B),
    onPrimaryContainer = Color(0xFFD1FAE5),
    secondary = Color(0xFFF59E0B), // Warm Gold
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF1E382B),
    onSecondaryContainer = Color(0xFFFFFFFF),
    tertiary = Color(0xFF34D399), // Mint Green
    onTertiary = Color(0xFF064E3B),
    background = Color(0xFF0A110D), // Deep dark obsidian background
    surface = Color(0xFF111C16), // Dark green-tinted card surface
    surfaceVariant = Color(0xFF182A21),
    onBackground = Color(0xFFFFFFFF), // Pure White Text
    onSurface = Color(0xFFFFFFFF), // Pure White Text
    onSurfaceVariant = Color(0xFFE5E7EB),
    outline = Color(0xFF244434)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to Dark Theme as requested
  dynamicColor: Boolean = false, // Keep intentional Emerald & White on Dark theme
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = DarkColorScheme, typography = Typography, content = content)
}

