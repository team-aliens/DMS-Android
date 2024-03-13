package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp

fun Modifier.clickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    ripple: Boolean = true,
    rippleColor: Color? = null,
    rippleRadius: Dp? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier = composed {
    clickable(
        onClick = onClick,
        enabled = enabled,
        onClickLabel = onClickLabel,
        indication = rememberRipple(
            color = rippleColor ?: DmsTheme.colorScheme.surfaceVariant,
            radius = rippleRadius ?: Dp.Unspecified,
        )
            .takeIf { ripple },
        interactionSource = remember { MutableInteractionSource() },
        role = role,
    )
}
