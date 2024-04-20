package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    elevation: Dp = ShadowDefaults.MediumElevation,
    shape: Shape = ShadowDefaults.RoundedShape,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color? = null,
    spotColor: Color? = null,
): Modifier = composed {
    shadow(
        elevation = elevation,
        shape = shape,
        clip = clip,
        ambientColor = ambientColor
            ?: DmsTheme.colorScheme.onSurfaceVariant.copy(alpha = ShadowDefaults.DefaultAlpha),
        spotColor = spotColor
            ?: DmsTheme.colorScheme.onSurfaceVariant.copy(alpha = ShadowDefaults.DefaultAlpha),
    )
}

object ShadowDefaults {
    val SmallElevation = 2.dp
    val MediumElevation = 4.dp
    val LargeElevation = 8.dp

    const val DefaultAlpha = 0.6f

    val RoundedShape = RoundedCornerShape(12.dp)
}
