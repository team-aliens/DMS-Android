package team.aliens.dms.android.designsystem.modifier

import androidx.compose.foundation.background
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import team.aliens.dms.android.designsystem.annotation.DormDeprecated

@DormDeprecated
@Stable
fun Modifier.dormGradientBackground(
    backgroundColor: Any,
) = run {
    when (backgroundColor) {
        is Color -> this.background(
            color = backgroundColor,
        )
        is Brush -> this.background(
            brush = backgroundColor,
        )
        else -> throw IllegalArgumentException()
    }
}
