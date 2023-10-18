package team.aliens.dms.android.designsystem.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import team.aliens.dms.android.designsystem.annotation.DormDeprecated
import team.aliens.dms.android.designsystem.theme.DmsTheme
import team.aliens.dms.android.designsystem.utils.runIf

@DormDeprecated
@Stable
fun Modifier.dormClickable(
    rippleEnabled: Boolean = true,
    rippleColor: Color? = null,
    runIf: Boolean = true,
    onClick: (() -> Unit)?,
) = runIf(runIf && onClick != null) {
    composed {
        clickable(
            onClick = onClick!!,
            indication = rememberRipple(
                color = rippleColor ?: DmsTheme.colors.primaryVariant,
            ).takeIf {
                rippleEnabled
            },
            interactionSource = remember { MutableInteractionSource() },
        )
    }
}