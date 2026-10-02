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
    tertiary = Color(0xFF38BDF8), // Soft Azure
    onTertiary = Color(0xFF0A1012),
    background = Color(0xFF0A1012), // Deep architectural charcoal slate (eye comfort)
    surface = Color(0xFF111A1E), // Frosted dark slate card
    surfaceVariant = Color(0xFF17242A),
    onBackground = Color(0xFFF1F5F9), // Comfortable crisp off-white text
    onSurface = Color(0xFFF1F5F9), // Comfortable crisp off-white text
    onSurfaceVariant = Color(0xFF94A3B8), // Soft slate secondary text
    outline = Color(0xFF22353F)
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to Dark Theme as requested
  dynamicColor: Boolean = false, // Keep intentional Emerald & White on Dark theme
  content: @Composable () -> Unit,
) {
  MaterialTheme(colorScheme = DarkColorScheme, typography = Typography, content = content)
}

