package team.aliens.dms.android.core.widget.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object WidgetTheme {
    val colors: ColorProviders
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProviders.current

}


@Composable
fun WidgetTheme(
    colors: ColorProviders = WidgetTheme.colors,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalColorProviders provides colors) {
        content()
    }
}

