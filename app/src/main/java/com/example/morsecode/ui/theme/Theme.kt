package com.example.morsecode.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary         = Color(0xFFCF9107),
    onPrimary       = Color(0xFFFFFFFF),
    surface         = Color(0xFF121212),
    onSurface       = Color(0xFFE0E0E0),
    surfaceVariant  = Color(0xFF2C2C2C),
    onSurfaceVariant= Color(0xFFB0B0B0)
)

private val LightColorScheme = lightColorScheme(
    primary         = Color(0xFFE89B1A),
    onPrimary       = Color(0xFF2C2C2C),
    surface         = Color(0xFFFFFFFF),
    onSurface       = Color(0xFF121212),
    surfaceVariant  = Color(0xFFF5F5F5),
    onSurfaceVariant= Color(0xFF616161)
)

@Composable
fun MorseCodeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}