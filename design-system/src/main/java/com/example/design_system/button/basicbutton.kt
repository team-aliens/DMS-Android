package com.example.design_system.component

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.modifier.dormClickable
import com.example.design_system.typography.ButtonText

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disbledColor: Color,
    rippleColor: Color,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (isPressed: Boolean) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val isPressed = interactionSource.collectIsPressedAsState()

    val btnColor =
        if (!rippleEnabled) disbledColor else if (isPressed.value) rippleColor else backgroundColor

    Box(
        modifier = modifier
            .dormClickable(
                rippleEnabled = rippleEnabled,
                rippleColor = rippleColor,
                onClick = onClick,
            )
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
fun BasicContainedButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disbledColor: Color,
    rippleColor: Color,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {

    BasicButton(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        disbledColor = disbledColor,
        rippleColor = rippleColor,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ) {
        ButtonText(
            text = text,
            color = textColor,
        )
    }
}

@Composable
fun BasicOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    disbledColor: Color,
    rippleColor: Color,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {

    BasicButton(
        modifier = modifier
            .border(
                width = 1.dp,
                color = backgroundColor,
            ),
        shape = shape,
        backgroundColor = Color.Transparent,
        disbledColor = disbledColor,
        rippleColor = rippleColor,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    ) {
        ButtonText(
            text = text,
            color = textColor,
        )
    }
}

@Composable
fun BasicContainedRoundButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disbledColor: Color,
    rippleColor: Color,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedButton(
        modifier = modifier,
        text = text,
        textColor = textColor,
        shape = RoundedCornerShape(round),
        backgroundColor = backgroundColor,
        disbledColor = disbledColor,
        rippleColor = rippleColor,
        rippleEnabled = rippleEnabled,
        onClick = onClick
    )
}

@Stable
private val BasicLargeButtonHeight = 48.dp

@Composable
fun BasicContainedRoundLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    disbledColor: Color,
    rippleColor: Color,
    rippleEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(BasicLargeButtonHeight),
        text = text,
        textColor = textColor,
        shape = RoundedCornerShape(round),
        backgroundColor = backgroundColor,
        disbledColor = disbledColor,
        rippleColor = rippleColor,
        rippleEnabled = rippleEnabled,
        onClick = onClick,
    )
}
