package com.memory.localdatastorage.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFD4537E),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFFBEAF0),
    secondary = androidx.compose.ui.graphics.Color(0xFF7F77DD),
    onSecondary = androidx.compose.ui.graphics.Color.White,
    background = androidx.compose.ui.graphics.Color(0xFFFFF5F8),
    surface = androidx.compose.ui.graphics.Color.White,
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFED93B1),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF4B1528),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF72243E),
    secondary = androidx.compose.ui.graphics.Color(0xFFAFA9EC),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF26215C),
    background = androidx.compose.ui.graphics.Color(0xFF1A1A1A),
    surface = androidx.compose.ui.graphics.Color(0xFF2A2A2A),
)

@Composable
fun NotesAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}