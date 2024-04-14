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
    elevation: Dp = ShadowDefaults.SmallElevation,
    shape: Shape = ShadowDefaults.RoundedShape,
    clip: Boolean = elevation > 0.dp,
    ambientColor: Color? = null,
    spotColor: Color? = null,
): Modifier = composed {
    shadow(
        elevation = elevation,
        shape = shape,
        clip = clip,
        ambientColor = ambientColor ?: DmsTheme.colorScheme.surfaceVariant,
        spotColor = spotColor ?: DmsTheme.colorScheme.surfaceVariant,
    )
}

object ShadowDefaults {
    val SmallElevation = 2.dp
    val MediumElevation = 4.dp
    val LargeElevation = 8.dp

    val RoundedShape = RoundedCornerShape(8.dp)
}
