package team.aliens.design_system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.design_system.annotation.DormDeprecated
import team.aliens.design_system.color.DormColor
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.modifier.dormShadow

@Stable
private val ToggleSwitchWidth = 38.dp

@Stable
private val ToggleSwitchHeight = 20.dp

@DormDeprecated
@Composable
fun ToggleSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {

    val yOffset: Dp by animateDpAsState(targetValue = if (checked) 17.dp else 0.dp)

    val circleBackgroundColor: Color by animateColorAsState(targetValue = if (checked) DormColor.DormPrimary else DormColor.Gray100)

    val backgroundColor: Color by animateColorAsState(targetValue = if (checked) DormColor.Lighten100 else DormColor.Gray500)

    Box(
        modifier = modifier.size(
            width = ToggleSwitchWidth,
            height = ToggleSwitchHeight,
        ).dormClickable(
            rippleEnabled = false,
        ) {
            onToggle(!checked)
        },
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(14.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp),
            ))

        Box(modifier = Modifier
            .padding(start = yOffset)
            .size(20.dp)
            .background(
                color = circleBackgroundColor,
                shape = CircleShape,
            )
            .dormShadow(
                color = DormColor.Gray400,
                offsetY = 1.dp,
            ))
    }
}

@Preview
@Composable
fun PreviewToggleSwitch() {
    var checked by remember { mutableStateOf(false) }

    ToggleSwitch(
        checked = checked,
        onToggle = { checked = !checked },
    )
}