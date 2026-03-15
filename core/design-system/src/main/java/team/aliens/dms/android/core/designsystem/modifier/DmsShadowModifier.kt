package team.aliens.dms.android.core.designsystem.modifier

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.dmsShadowModifier(
    dmsShadowType: DmsShadowType,
    shape: Shape = CircleShape,
): Modifier {
    return when (dmsShadowType) {
        DmsShadowType.Light10 -> this.dmsDropShadow(
            shape = shape,
            color = Color.Black.copy(alpha = 0.08f),
            blur = 10.dp,
            offsetX = 0.dp,
            offsetY = 4.dp,
        )

        DmsShadowType.Light20 -> this.dmsDropShadow(
            shape = shape,
            color = Color.Black.copy(alpha = 0.1f),
            blur = 15.dp,
            offsetX = 0.dp,
            offsetY = 1.dp,
        )

        DmsShadowType.Standard -> this.dmsDropShadow(
            shape = shape,
            color = Color.Black.copy(alpha = 0.19f),
            blur = 20.dp,
            offsetX = 0.dp,
            offsetY = 3.dp,
        )

        DmsShadowType.Dark10 -> this.dmsDropShadow(
            shape = shape,
            color = Color.Black.copy(alpha = 0.25f),
            blur = 14.dp,
            offsetX = 0.dp,
            offsetY = 14.dp,
        )

        DmsShadowType.Dark20 -> this.dmsDropShadow(
            shape = shape,
            color = Color.Black.copy(alpha = 0.30f),
            blur = 19.dp,
            offsetX = 0.dp,
            offsetY = 19.dp,
        )
    }
}

enum class DmsShadowType {
    Light10, Light20, Standard, Dark10, Dark20
}
