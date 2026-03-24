package team.aliens.dms.android.core.designsystem.tab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.lBodyB
import team.aliens.dms.android.core.designsystem.lBodyM

@Composable
fun DmsTab(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = DmsTheme.colorScheme.tertiaryContainer,
    unselectedContentColor: Color = DmsTheme.colorScheme.inverseSurface,
) {
    val (textStyle, textColor) = if (selected) DmsTheme.typography.lBodyB to selectedContentColor else DmsTheme.typography.lBodyM to unselectedContentColor
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = {
            Text(
                text = text,
                style = textStyle,
                color = textColor,
            )
        },
        icon = icon,
        interactionSource = interactionSource,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
    )
}
