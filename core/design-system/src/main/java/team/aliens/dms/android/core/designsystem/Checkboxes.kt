package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CheckboxColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox as MaterialCheckbox
import androidx.compose.material3.CheckboxDefaults as MaterialCheckboxDefaults

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = MaterialCheckbox(
    checked = checked,
    onCheckedChange = onCheckedChange,
    modifier = modifier,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
)

object CheckboxDefaults {

    const val SelectedDisabledContainerOpacity = 0.38f

    const val UnselectedDisabledContainerOpacity = 0.38f

    @Composable
    fun colors(
        checkedColor: Color = DmsTheme.colorScheme.primary,
        uncheckedColor: Color = DmsTheme.colorScheme.onSurfaceVariant,
        checkmarkColor: Color = DmsTheme.colorScheme.onPrimary,
        disabledCheckedColor: Color = checkedColor.copy(alpha = SelectedDisabledContainerOpacity),
        disabledUncheckedColor: Color = uncheckedColor.copy(alpha = UnselectedDisabledContainerOpacity),
        disabledIndeterminateColor: Color = disabledCheckedColor,
    ): CheckboxColors = MaterialCheckboxDefaults.colors(
        checkedColor = checkedColor,
        uncheckedColor = uncheckedColor,
        checkmarkColor = checkmarkColor,
        disabledCheckedColor = disabledCheckedColor,
        disabledUncheckedColor = disabledUncheckedColor,
        disabledIndeterminateColor = disabledIndeterminateColor,
    )
}

@Preview
@Composable
private fun CheckboxPreview() {
    val (checked, onCheckedChange) = remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
