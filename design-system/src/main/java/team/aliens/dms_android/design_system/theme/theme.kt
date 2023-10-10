package team.aliens.dms_android.design_system.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import team.aliens.dms_android.design_system.annotation.DormDeprecated
import team.aliens.dms_android.design_system.color.DormColor

@SuppressLint("ConflictingOnColor")
private val lightColorPalette = lightColors(
    primary = DormColor.DormPrimary,
    onPrimary = DormColor.Gray100,
    secondary = DormColor.Lighten200, // primary container
    onSecondary = DormColor.Darken200, // on primary container
    error = DormColor.Error,
    onError = DormColor.Gray100,
    background = DormColor.Gray200,
    onBackground = DormColor.Gray1000,
    surface = DormColor.Gray100,
    onSurface = DormColor.Gray900,
    primaryVariant = DormColor.Gray500, // icon
    secondaryVariant = DormColor.Gray300, // line
)

private val darkColorPalette = darkColors(
    primary = DormColor.DormPrimary,
    onPrimary = DormColor.Gray100,
    secondary = DormColor.Lighten100, // primary container
    onSecondary = DormColor.Darken100, // on primary container
    onError = DormColor.Gray100,
    background = DormColor.Gray900,
    onBackground = DormColor.Gray100,
    surface = DormColor.Gray800,
    onSurface = DormColor.Gray200,
    primaryVariant = DormColor.Gray500, // icon
    secondaryVariant = DormColor.Gray700, // line
)

@DormDeprecated
@Composable
fun DormTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { if (darkTheme) darkColorPalette else lightColorPalette }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
    ) {
        MaterialTheme(
            colors = DormTheme.colors,
            content = content,
        )
    }
}

@Stable
object DormTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

internal val LocalColors = staticCompositionLocalOf { lightColors() }
