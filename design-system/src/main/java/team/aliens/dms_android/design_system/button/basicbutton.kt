package team.aliens.dms_android.design_system.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms_android.design_system.modifier.dormClickable
import team.aliens.dms_android.design_system.theme.DormTheme
import team.aliens.dms_android.design_system.typography.ButtonText
import team.aliens.dms_android.design_system.utils.runIf

@Stable
private val disabledAlpha = 0.5f

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (isPressed: Boolean) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val btnColor = if (isPressed) rippleColor else backgroundColor

    Surface(
        modifier = modifier
            .background(DormTheme.colors.background)
            .clip(shape),
    ) {
        Box(
            modifier = Modifier
                .alpha(if (enabled) 1f else disabledAlpha)
                .runIf(enabled) {
                    dormClickable(
                        rippleEnabled = true,
                        rippleColor = rippleColor,
                        onClick = onClick,
                    )
                }
                .background(btnColor)
                .padding(
                    vertical = 12.dp,
                    horizontal = 20.dp,
                ),
            contentAlignment = Alignment.Center,
        ) {
            content(isPressed)
        }
    }
}

@Composable
private fun BasicContainedButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    shape: Shape = RectangleShape,
    backgroundColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicButton(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
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
private fun BasicOutlinedButton(
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
        targetValue = if (enabled) backgroundColor else disabledColor,
        label = "outlineBackgroundColor",
    )

    val outlineTextColor by animateColorAsState(
        targetValue = if (enabled) textColor else disabledColor,
        label = "outlineTextColor",
    )

    BasicButton(
        modifier = modifier.border(
            width = 1.dp,
            color = outlineBackgroundColor,
            shape = shape,
        ),
        shape = shape,
        backgroundColor = Color.Transparent,
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
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
internal fun BasicOutlinedRoundButton(
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
    BasicOutlinedButton(
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
internal fun BasicContainedRoundLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    round: Dp = 0.dp,
    backgroundColor: Color,
    rippleColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    BasicContainedRoundButton(
        modifier = modifier.fillMaxWidth(),
        text = text,
        textColor = textColor,
        round = round,
        backgroundColor = backgroundColor,
        rippleColor = rippleColor,
        enabled = enabled,
        onClick = onClick,
    )
}

@Composable
internal fun BasicOutlinedRoundLargeButton(
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
    BasicOutlinedRoundButton(
        modifier = modifier.fillMaxWidth(),
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
