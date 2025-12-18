package team.aliens.dms.android.core.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.RadioButton as MaterialRadioButton
import androidx.compose.material3.RadioButtonDefaults as MaterialRadioButtonDefaults

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = MaterialRadioButton(
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
)

object RadioButtonDefaults {

    const val DisabledSelectedOverlay = 0.38f
    const val DisabledUnselectedOverlay = 0.38f

    @Composable
    fun colors(
        selectedColor: Color = DmsTheme.colorScheme.primary,
        unselectedColor: Color = DmsTheme.colorScheme.surfaceVariant,
        disabledSelectedColor: Color = selectedColor
            .copy(alpha = DisabledSelectedOverlay),
        disabledUnselectedColor: Color = unselectedColor
            .copy(alpha = DisabledUnselectedOverlay),
    ): RadioButtonColors = MaterialRadioButtonDefaults.colors(
        selectedColor = selectedColor,
        unselectedColor = unselectedColor,
        disabledSelectedColor = disabledSelectedColor,
        disabledUnselectedColor = disabledUnselectedColor,
    )
}

@Preview(showSystemUi = true)
@Composable
private fun RadioButtonPreview() {
    val selectedValue = remember { mutableStateOf("") }
    val label = "Item"

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selectedValue.value == label,
            onClick = { selectedValue.value = label },
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = label,
        )
    }
}
