package com.example.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.color.DormColor
import com.example.design_system.icon.DormIcon
import com.example.design_system.modifier.dormClickable
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
    rippleEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val backgroundColor = if (rippleEnabled) selectedColor else disabledSelectedColor

    val borderColor = if (rippleEnabled) unSelectedColor else disabledUnSelectedColor

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
            .dormClickable(
                rippleEnabled = rippleEnabled,
                rippleColor = null,
            ) {
                onCheckedChange(!checked)
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
    rippleEnabled: Boolean = true,
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
        rippleEnabled = rippleEnabled,
    ) {
        Image(
            painter = painterResource(
                id = icon.drawableId
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
    rippleEnabled: Boolean = true,
    icon: DormIcon = DormIcon.Check
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
        rippleEnabled = rippleEnabled,
        icon = icon,
    )
}

@Stable
private val DefaultCheckBoxRound = 2.dp

@Stable
private val DefaultCheckBoxSize = 24.dp

@Composable
fun BlueRoundCheckBox(
    modifier: Modifier = Modifier,
    round: Dp = DefaultCheckBoxRound,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    rippleEnabled: Boolean = true,
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
        rippleEnabled = rippleEnabled,
        icon = DormIcon.Check,
    )
}

@Preview
@Composable
fun PreviewCheckBox() {
    var checked by remember { mutableStateOf(true) }

    BlueRoundCheckBox(
        checked = checked,
        onCheckedChange = { checked = !checked },
    )
}