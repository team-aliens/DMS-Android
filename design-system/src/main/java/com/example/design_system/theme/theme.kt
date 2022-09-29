package com.example.design_system.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.design_system.color.DormColor

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = DormColor.DormPrimary,
    background = DormColor.Gray200,
    surface = DormColor.Gray100,
    error = DormColor.Error,
    onError = DormColor.Gray100,
    onPrimary = DormColor.Gray100,
    onBackground = DormColor.Gray900,
    onSurface = DormColor.Gray900,
)

private val DarkColorPalette = darkColors(
    primary = DormColor.DormPrimary,
    background = DormColor.Gray800,
    surface = DormColor.Gray800,
    error = DormColor.Error,
    onError = DormColor.Gray100,
    onPrimary = DormColor.Gray100,
    onBackground = DormColor.Gray100,
    onSurface = DormColor.Gray100,
)

@Composable
fun DormTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content,
    )
}
