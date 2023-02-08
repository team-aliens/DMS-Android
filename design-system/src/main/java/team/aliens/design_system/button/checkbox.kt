package team.aliens.design_system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.Body5
import com.example.design_system.utils.runIf

@Composable
fun BasicCheckBox(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    selectedColor: Color,
    unSelectedColor: Color,
    disabledSelectedColor: Color,
    disabledUnSelectedColor: Color,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val backgroundColor: Color by animateColorAsState(
        if (enabled) selectedColor else disabledSelectedColor
    )

    val borderColor: Color by animateColorAsState(
        if (enabled) unSelectedColor else disabledUnSelectedColor
    )

    Box(
        modifier = modifier
            .background(
                color = if (checked) backgroundColor else Color.Transparent,
                shape = shape,
            )
            .runIf(!checked) {
                composed {
                    border(
                        width = 2.dp,
                        color = borderColor,
                    )
                }
            }
            .runIf(enabled) {
                composed {
                    dormClickable(
                        rippleEnabled = enabled,
                        rippleColor = null,
                    ) {
                        onCheckedChange(!checked)
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            content()
        }
    }
}

@Composable
fun BasicIconCheckBox(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    selectedColor: Color,
    unSelectedColor: Color,
    disabledSelectedColor: Color,
    disabledUnSelectedColor: Color,
    enabled: Boolean = true,
    icon: DormIcon = DormIcon.Check
) {
    BasicCheckBox(
        modifier = modifier,
        shape = shape,
        checked = checked,
        onCheckedChange = onCheckedChange,
        selectedColor = selectedColor,
        unSelectedColor = unSelectedColor,
        disabledSelectedColor = disabledSelectedColor,
        disabledUnSelectedColor = disabledUnSelectedColor,
        enabled = enabled,
    ) {
        Image(
            painter = painterResource(
                id = icon.drawableId,
            ),
            contentDescription = null,
        )
    }
}

@Composable
fun BasicRoundIconCheckBox(
    modifier: Modifier = Modifier,
    round: Dp = 0.dp,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    selectedColor: Color,
    unSelectedColor: Color,
    disabledSelectedColor: Color,
    disabledUnSelectedColor: Color,
    enabled: Boolean = true,
    icon: DormIcon = DormIcon.Check,
) {
    BasicIconCheckBox(
        modifier = modifier,
        shape = RoundedCornerShape(round),
        checked = checked,
        onCheckedChange = onCheckedChange,
        selectedColor = selectedColor,
        unSelectedColor = unSelectedColor,
        disabledSelectedColor = disabledSelectedColor,
        disabledUnSelectedColor = disabledUnSelectedColor,
        enabled = enabled,
        icon = icon,
    )
}

@Stable
private val DefaultCheckBoxRound = 2.dp

@Stable
private val DefaultCheckBoxSize = 24.dp

@Composable
fun DormCheckBox(
    modifier: Modifier = Modifier,
    round: Dp = DefaultCheckBoxRound,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    BasicRoundIconCheckBox(
        modifier = modifier.size(DefaultCheckBoxSize),
        round = round,
        checked = checked,
        onCheckedChange = onCheckedChange,
        selectedColor = DormColor.DormPrimary,
        unSelectedColor = DormColor.Gray500,
        disabledSelectedColor = DormColor.Lighten100,
        disabledUnSelectedColor = DormColor.Gray300,
        enabled = enabled,
        icon = DormIcon.Check,
    )
}

@Composable
fun DormTextCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    Row(
        modifier = modifier
            .runIf(enabled) {
                composed {
                    dormClickable(
                        rippleEnabled = false,
                        rippleColor = null,
                    ) {
                        onCheckedChange(!checked)
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DormCheckBox(
            modifier = modifier,
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
        )

        Spacer(modifier = Modifier.width(15.dp))

        Box(
            modifier = Modifier
                .offset(
                    y = (-1.2).dp,
                ),
        ) {
            Body5(
                text = text,
                color = DormColor.Gray700,
            )
        }
    }
}