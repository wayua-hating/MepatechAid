package com.wayuyu.mepatech.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = CyanAccent,

    background = DarkBackground,
    surface = DarkSurface,

    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,

    error = ErrorRed
)

// Optional: future light mode support
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = CyanAccent,
    background = TextWhite,
    surface = TextWhite,
    onPrimary = DarkBackground,
    onBackground = DarkBackground,
    onSurface = DarkBackground,
    error = ErrorRed
)

@Composable
fun MepatechTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}