package team.aliens.dms.android.core.designsystem.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = DmsTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {

}

/*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
import team.aliens.dms.android.designsystem.modifier.dormClickable
import team.aliens.dms.android.designsystem.theme.DmsTheme
import team.aliens.dms.android.designsystem.typography.ButtonText
import team.aliens.dms.android.designsystem.utils.runIf

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

    Button(onClick = { */
/*TODO*//*
 }) {

    }
    Surface(
        modifier = modifier
            .background(DmsTheme.colors.background)
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
*/
