package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Switch as MaterialSwitch
import androidx.compose.material3.SwitchDefaults as MaterialSwitchDefaults

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = MaterialSwitch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    modifier = modifier,
    thumbContent = thumbContent,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
)

object SwitchDefaults {

    const val DisabledThumbOverlay = 0.38f

    const val DisabledTrackOverlay = 0.12f

    const val DisabledIconOverlay = 0.38f

    @Composable
    fun colors(
        checkedThumbColor: Color = DmsTheme.colorScheme.onPrimary,
        checkedTrackColor: Color = DmsTheme.colorScheme.primary,
        checkedBorderColor: Color = Color.Transparent,
        checkedIconColor: Color = DmsTheme.colorScheme.primary,
        uncheckedThumbColor: Color = DmsTheme.colorScheme.onPrimary,
        uncheckedTrackColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        uncheckedBorderColor: Color = Color.Transparent,
        uncheckedIconColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        disabledCheckedThumbColor: Color = checkedThumbColor
            .copy(alpha = DisabledThumbOverlay)
            .compositeOver(DmsTheme.colorScheme.surface),
        disabledCheckedTrackColor: Color = checkedTrackColor
            .copy(alpha = DisabledTrackOverlay)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledCheckedBorderColor: Color = Color.Transparent,
        disabledCheckedIconColor: Color = checkedIconColor
            .copy(alpha = DisabledIconOverlay)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedThumbColor: Color = uncheckedThumbColor
            .copy(alpha = DisabledThumbOverlay)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedTrackColor: Color = uncheckedTrackColor
            .copy(alpha = DisabledTrackOverlay)
            .compositeOver(MaterialTheme.colorScheme.surface),
        disabledUncheckedBorderColor: Color = Color.Transparent,
        disabledUncheckedIconColor: Color = uncheckedIconColor
            .copy(alpha = DisabledIconOverlay)
            .compositeOver(MaterialTheme.colorScheme.surface),
    ): SwitchColors = MaterialSwitchDefaults.colors(
        checkedThumbColor = checkedThumbColor,
        checkedTrackColor = checkedTrackColor,
        checkedBorderColor = checkedBorderColor,
        checkedIconColor = checkedIconColor,
        uncheckedThumbColor = uncheckedThumbColor,
        uncheckedTrackColor = uncheckedTrackColor,
        uncheckedBorderColor = uncheckedBorderColor,
        uncheckedIconColor = uncheckedIconColor,
        disabledCheckedThumbColor = disabledCheckedThumbColor,
        disabledCheckedTrackColor = disabledCheckedTrackColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledCheckedIconColor = disabledCheckedIconColor,
        disabledUncheckedThumbColor = disabledUncheckedThumbColor,
        disabledUncheckedTrackColor = disabledUncheckedTrackColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        disabledUncheckedIconColor = disabledUncheckedIconColor,
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SwitchPreview() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val (checked, onCheckedChange) = remember { mutableStateOf(false) }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
