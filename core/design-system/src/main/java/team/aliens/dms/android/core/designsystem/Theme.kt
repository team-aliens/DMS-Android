package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
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

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalShapes provides DmsTheme.shapes,
        LocalTypography provides DmsTheme.typography,
    ) {
        ToastLayout(toastState = rememberToastState()) {
            MaterialTheme(
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
