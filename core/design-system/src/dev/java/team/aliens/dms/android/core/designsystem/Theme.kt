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
    primary = DmsColor.Light.blue400,
    onPrimary = DmsColor.Light.gray50,
    primaryContainer = DmsColor.Light.blue100,
    onPrimaryContainer = DmsColor.Light.blue500,
    error = DmsColor.Light.red300,
    onError = DmsColor.Light.gray50,
    errorContainer = DmsColor.Light.red50,
    onErrorContainer = DmsColor.Light.red500,
    secondary = DmsColor.Light.blue300,
    onSecondary = DmsColor.Light.gray50,
    secondaryContainer = DmsColor.Light.blue100,
    onSecondaryContainer = DmsColor.Light.blue500,
    tertiary = DmsColor.Light.blue200,
    onTertiary = DmsColor.Light.gray50,
    tertiaryContainer = DmsColor.Light.blue50,
    onTertiaryContainer = DmsColor.Light.blue500,
    background = DmsColor.Light.gray50,
    onBackground = DmsColor.Light.gray900,
    surface = DmsColor.Light.gray100,
    onSurface = DmsColor.Light.gray900,
    surfaceVariant = DmsColor.Light.gray200,
    onSurfaceVariant = DmsColor.Light.gray700,
    inverseSurface = DmsColor.Light.gray800,
    inverseOnSurface = DmsColor.Light.gray50,
    outline = DmsColor.Light.gray400,
    outlineVariant = DmsColor.Light.gray300,
    surfaceTint = DmsColor.Light.blue300,
)

private val DmsDarkColorScheme = darkColorScheme(
    primary = DmsColor.Dark.blue200,
    onPrimary = DmsColor.Dark.gray900,
    primaryContainer = DmsColor.Dark.blue300,
    onPrimaryContainer = DmsColor.Dark.gray50,
    error = DmsColor.Dark.red200,
    onError = DmsColor.Dark.gray900,
    errorContainer = DmsColor.Dark.red300,
    onErrorContainer = DmsColor.Dark.gray50,
    secondary = DmsColor.Dark.blue100,
    onSecondary = DmsColor.Dark.gray900,
    secondaryContainer = DmsColor.Dark.blue200,
    onSecondaryContainer = DmsColor.Dark.gray50,
    tertiary = DmsColor.Dark.blue300,
    onTertiary = DmsColor.Dark.gray900,
    tertiaryContainer = DmsColor.Dark.blue100,
    onTertiaryContainer = DmsColor.Dark.gray50,
    background = DmsColor.Dark.gray900,
    onBackground = DmsColor.Dark.gray100,
    surface = DmsColor.Dark.gray800,
    onSurface = DmsColor.Dark.gray100,
    surfaceVariant = DmsColor.Dark.gray700,
    onSurfaceVariant = DmsColor.Dark.gray200,
    inverseSurface = DmsColor.Dark.gray100,
    inverseOnSurface = DmsColor.Dark.gray900,
    outline = DmsColor.Dark.gray500,
    outlineVariant = DmsColor.Dark.gray400,
    surfaceTint = DmsColor.Dark.blue200,
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
