package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.material3.Typography as MaterialTypography

private val lightColorScheme = lightColorScheme(
    primary = DmsColor.Light.blue50,
    onPrimary = DmsColor.Light.blue100,
    primaryContainer = DmsColor.Light.blue200,
    onPrimaryContainer = DmsColor.Light.blue300,
    inversePrimary = DmsColor.Light.blue400,
    secondary = DmsColor.Light.blue500,
    surface = DmsColor.Light.gray50,
    onSurface = DmsColor.Light.gray100,
    surfaceVariant = DmsColor.Light.gray200,
    onSurfaceVariant = DmsColor.Light.gray300,
    inverseSurface = DmsColor.Light.gray400,
    inverseOnSurface = DmsColor.Light.gray500,
    tertiaryContainer = DmsColor.Light.gray600,
    onTertiaryContainer = DmsColor.Light.gray700,
    surfaceBright = DmsColor.Light.gray800,
    surfaceContainer = DmsColor.Light.gray900,
    error = DmsColor.Light.red50,
    onError = DmsColor.Light.red100,
    errorContainer = DmsColor.Light.red200,
    onErrorContainer = DmsColor.Light.red300,
    outline = DmsColor.Light.red400,
    outlineVariant = DmsColor.Light.red500,
    background = DmsColor.Light.background,
    onBackground = DmsColor.Light.black,
    surfaceTint = DmsColor.Light.container,
    scrim = DmsColor.Light.button,
    tertiary = DmsColor.Light.hover,
    onTertiary = DmsColor.Light.pressed,
)

private val darkColorScheme = darkColorScheme(
    primary = DmsColor.Dark.blue50,
    onPrimary = DmsColor.Dark.blue100,
    primaryContainer = DmsColor.Dark.blue200,
    onPrimaryContainer = DmsColor.Dark.blue300,
    inversePrimary = DmsColor.Dark.blue400,
    secondary = DmsColor.Dark.blue500,
    surface = DmsColor.Dark.gray50,
    onSurface = DmsColor.Dark.gray100,
    surfaceVariant = DmsColor.Dark.gray200,
    onSurfaceVariant = DmsColor.Dark.gray300,
    inverseSurface = DmsColor.Dark.gray400,
    inverseOnSurface = DmsColor.Dark.gray500,
    tertiaryContainer = DmsColor.Dark.gray600,
    onTertiaryContainer = DmsColor.Dark.gray700,
    surfaceBright = DmsColor.Dark.gray800,
    surfaceContainer = DmsColor.Dark.gray900,
    error = DmsColor.Dark.red50,
    onError = DmsColor.Dark.red100,
    errorContainer = DmsColor.Dark.red200,
    onErrorContainer = DmsColor.Dark.red300,
    outline = DmsColor.Dark.red400,
    outlineVariant = DmsColor.Dark.red500,
    background = DmsColor.Dark.background,
    onBackground = DmsColor.Dark.black,
    surfaceTint = DmsColor.Dark.container,
    scrim = DmsColor.Dark.button,
    tertiary = DmsColor.Dark.hover,
    onTertiary = DmsColor.Dark.pressed,
)

val LocalColors = staticCompositionLocalOf { lightColorScheme }

@Composable
fun DmsTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val m3ColorScheme = if (isDarkTheme) darkColorScheme else lightColorScheme

    val dmsTypography = DmsTheme.typography

    CompositionLocalProvider(
        LocalColors provides m3ColorScheme,
        LocalTypography provides dmsTypography,
        // TODO :: shape 구현
    ) {
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
        ) {
            content()
        }
    }
}

@Stable
object DmsTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}
