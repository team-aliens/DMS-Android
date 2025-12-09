package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography as MaterialTypography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

private val DmsLightColorScheme = lightColorScheme(
    primary = DmsColor.Light.blue300,
    onPrimary = DmsColor.Light.gray50,
    primaryContainer = DmsColor.Light.blue200,
    onPrimaryContainer = DmsColor.Light.gray100,
    inversePrimary = DmsColor.Light.blue400,
    secondary = DmsColor.Light.blue500,
    onSecondary = DmsColor.Light.gray200,
    secondaryContainer = DmsColor.Light.blue100,
    onSecondaryContainer = DmsColor.Light.gray300,
    tertiary = DmsColor.Light.blue50,
    onTertiary = DmsColor.Light.gray500,
    tertiaryContainer = DmsColor.Light.gray600,
    onTertiaryContainer = DmsColor.Light.gray700,
    background = DmsColor.Light.background,
    onBackground = DmsColor.Light.gray900,
    surface = DmsColor.Light.container,
    onSurface = DmsColor.Light.gray800,
    surfaceVariant = DmsColor.Light.gray400,
    onSurfaceVariant = DmsColor.Light.button,
    surfaceTint = DmsColor.Light.hover,
    inverseSurface = DmsColor.Light.red50,
    inverseOnSurface = DmsColor.Light.red100,
    error = DmsColor.Light.red200,
    onError = DmsColor.Light.red300,
    errorContainer = DmsColor.Light.red400,
    onErrorContainer = DmsColor.Light.red500,
    outline = DmsColor.Light.black,
    outlineVariant = DmsColor.Light.pressed,
)

private val DmsDarkColorScheme = darkColorScheme(
    primary = DmsColor.Dark.blue300,
    onPrimary = DmsColor.Dark.gray50,
    primaryContainer = DmsColor.Dark.blue200,
    onPrimaryContainer = DmsColor.Dark.gray100,
    inversePrimary = DmsColor.Dark.blue400,
    secondary = DmsColor.Dark.blue500,
    onSecondary = DmsColor.Dark.gray200,
    secondaryContainer = DmsColor.Dark.blue100,
    onSecondaryContainer = DmsColor.Dark.gray300,
    tertiary = DmsColor.Dark.blue50,
    onTertiary = DmsColor.Dark.gray500,
    tertiaryContainer = DmsColor.Dark.gray600,
    onTertiaryContainer = DmsColor.Dark.gray700,
    background = DmsColor.Dark.background,
    onBackground = DmsColor.Dark.gray900,
    surface = DmsColor.Dark.container,
    onSurface = DmsColor.Dark.gray800,
    surfaceVariant = DmsColor.Dark.gray400,
    onSurfaceVariant = DmsColor.Dark.button,
    surfaceTint = DmsColor.Dark.hover,
    inverseSurface = DmsColor.Dark.red50,
    inverseOnSurface = DmsColor.Dark.red100,
    error = DmsColor.Dark.red200,
    onError = DmsColor.Dark.red300,
    errorContainer = DmsColor.Dark.red400,
    onErrorContainer = DmsColor.Dark.red500,
    outline = DmsColor.Dark.black,
    outlineVariant = DmsColor.Dark.pressed,
)

@Composable
fun DmsTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val m3ColorScheme = if (isDarkTheme) DmsDarkColorScheme else DmsLightColorScheme

    val customColorScheme = remember(isDarkTheme) {
        if (isDarkTheme) {
            darkColors()
        } else {
            lightColors()
        }
    }

    val dmsTypography = DmsTheme.typography

    CompositionLocalProvider(
        LocalColors provides customColorScheme,
        LocalShapes provides DmsTheme.shapes,
        LocalTypography provides dmsTypography,
    ) {
        ToastLayout(toastState = rememberToastState()) {
            MaterialTheme(
                colorScheme = m3ColorScheme,
                typography = MaterialTypography(
                    headlineLarge = dmsTypography.headline1,
                    headlineMedium = dmsTypography.headline2,
                    headlineSmall = dmsTypography.headline3,
                    titleLarge = dmsTypography.title1,
                    titleMedium = dmsTypography.title2,
                    titleSmall = dmsTypography.title3,
                    bodyLarge = dmsTypography.body1,
                    bodyMedium = dmsTypography.body2,
                    bodySmall = dmsTypography.body3,
                    labelLarge = dmsTypography.button,
                    labelMedium = dmsTypography.caption,
                    labelSmall = dmsTypography.overline,
                ),
                content = content,
            )
        }
    }
}

@Stable
object DmsTheme {
    val colorScheme: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
