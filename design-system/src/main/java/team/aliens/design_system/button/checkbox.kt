package team.aliens.design_system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.design_system.extension.Space
import team.aliens.design_system.icon.DormIcon
import team.aliens.design_system.modifier.dormClickable
import team.aliens.design_system.theme.DormTheme
import team.aliens.design_system.typography.Body3
import team.aliens.design_system.utils.runIf

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
    val backgroundColor: Color by animateColorAsState(if (enabled) selectedColor else disabledSelectedColor)

    val borderColor: Color by animateColorAsState(if (enabled) unSelectedColor else disabledUnSelectedColor)

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(2.dp))
            .background(
                color = if (checked) backgroundColor else Color.Transparent,
            ).runIf(!checked) {
                composed {
                    border(
                        shape = RoundedCornerShape(2.dp),
                        width = 2.dp,
                        color = borderColor,
                    )
                }
            }.runIf(enabled) {
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
    icon: DormIcon = DormIcon.Check,
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
        selectedColor = DormTheme.colors.primary,
        unSelectedColor = DormTheme.colors.primaryVariant,
        disabledSelectedColor = DormTheme.colors.secondary,
        disabledUnSelectedColor = DormTheme.colors.secondaryVariant,
        enabled = enabled,
        icon = DormIcon.Check
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
        modifier = modifier.runIf(enabled) {
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

        Space(space = 15.dp)

        Box(
            modifier = Modifier.offset(
                y = (-1.2).dp,
            ),
        ) {
            Body3(
                text = text,
                color = DormTheme.colors.primaryVariant,
            )
        }
    }
}