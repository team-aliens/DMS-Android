package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography as MaterialTypography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun DmsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = remember(darkTheme) {
        if (darkTheme) {
            darkColors()
        } else {
            lightColors()
        }
    }

    val dmsTypography = DmsTheme.typography

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalShapes provides DmsTheme.shapes,
        LocalTypography provides dmsTypography,
    ) {
        ToastLayout(toastState = rememberToastState()) {
            MaterialTheme(
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
