package com.example.design_system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.ButtonText
import com.example.design_system.utils.runIf

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disabledCOlor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (isPressed: Boolean) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val isPressed = interactionSource.collectIsPressedAsState()

    val btnColor =
        if (!enabled) disabledCOlor else if (isPressed.value) rippleColor else backgroundColor

    Box(
        modifier = modifier
            .runIf(enabled) {
                composed {
                    dormClickable(
                        rippleEnabled = enabled,
                        rippleColor = rippleColor,
                        onClick = onClick,
                    )
                }
            }
            .background(
                color = btnColor,
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        content(isPressed.value)
    }
}

@Composable
private fun BasicContainedButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {

    BasicButton(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        disabledCOlor = disabledColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    ) {
        ButtonText(
            text = text,
            color = textColor,
        )
    }
}

@Composable
private fun BasicOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {

    val outlineBackgroundColor by animateColorAsState(
        if(enabled) backgroundColor else disabledColor
    )

    val outlineTextColor by animateColorAsState(
        if(enabled) textColor else disabledColor
    )

    BasicButton(
        modifier = modifier
            .border(
                width = 1.dp,
                color = outlineBackgroundColor,
                shape = shape,
            ),
        shape = shape,
        backgroundColor = Color.Transparent,
        disabledCOlor = Color.Transparent,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    ) {
        ButtonText(
            text = text,
            color = outlineTextColor,
        )
    }
}

@Composable
internal fun BasicContainedRoundButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        shape = RoundedCornerShape(round),
        backgroundColor = backgroundColor,
        disabledColor = disabledColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
internal fun BasicOutlineRoundButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicOutlineButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        shape = RoundedCornerShape(round),
        backgroundColor = backgroundColor,
        disabledColor = disabledColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Stable
private val BasicLargeButtonHeight = 48.dp

@Composable
internal fun BasicContainedRoundLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundButton(
        modifier = modifier
            .fillMaxWidth()
            .height(BasicLargeButtonHeight),
        text = text,
        textColor = textColor,
        round = round,
        backgroundColor = backgroundColor,
        disabledColor = disabledColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
internal fun BasicOutlineRoundLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disabledColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicOutlineRoundButton(
        modifier = modifier
            .fillMaxWidth()
            .height(BasicLargeButtonHeight),
        text = text,
        textColor = textColor,
        round = round,
        backgroundColor = backgroundColor,
        disabledColor = disabledColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}
