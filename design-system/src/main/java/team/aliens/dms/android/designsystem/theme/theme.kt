package team.aliens.dms.android.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import team.aliens.dms.android.designsystem.color.Colors
import team.aliens.dms.android.designsystem.color.LocalColors

@Composable
fun DmsTheme(
    colors: Colors = DmsTheme.colors,
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}

@Stable
object DmsTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}
