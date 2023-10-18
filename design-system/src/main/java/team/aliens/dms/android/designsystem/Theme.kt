package team.aliens.dms.android.designsystem

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
    shapes: Shapes = DmsTheme.shapes,
    content: @Composable () -> Unit,
) {
    val rememberedColors = remember { colors.copy() }
    val rememberedShapes = remember { shapes.copy() }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalShapes provides rememberedShapes,
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

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}
