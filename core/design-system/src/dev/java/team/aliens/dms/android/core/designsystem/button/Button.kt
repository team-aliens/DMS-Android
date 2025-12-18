package team.aliens.dms.android.core.designsystem.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.indecator.DmsDotsLoadingIndicator
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.util.DEFAULT_PRESS_DEPTH
import team.aliens.dms.android.core.designsystem.util.MIN_PRESS_DEPTH
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.core.designsystem.util.keyboardAsState
import team.aliens.dms.android.core.designsystem.util.modifyIf

data class ButtonState(
    val enabled: ButtonTheme,
    val pressed: ButtonTheme,
    val disabled: ButtonTheme,
)

enum class ButtonColor {
    Primary,
    Gray,
    Error,
}

enum class ButtonType {
    Contained,
    Text,
    Underline,
}

data class ButtonTheme(
    val textColor: Color,
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
)

@Composable
private fun ButtonColor.containedColorScheme() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.surface,
            backgroundColor = DmsTheme.colorScheme.onPrimaryContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.surface,
            backgroundColor = DmsTheme.colorScheme.inversePrimary,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.surfaceVariant,
            backgroundColor = DmsTheme.colorScheme.onPrimary,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.surfaceTint,
            backgroundColor = DmsTheme.colorScheme.inverseOnSurface,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.surfaceTint,
            backgroundColor = DmsTheme.colorScheme.surfaceBright,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.surfaceTint,
            backgroundColor = DmsTheme.colorScheme.surfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.surface,
            backgroundColor = DmsTheme.colorScheme.onErrorContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.surface,
            backgroundColor = DmsTheme.colorScheme.onErrorContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.inverseSurface,
            backgroundColor = DmsTheme.colorScheme.outlineVariant,
        ),
    )
}

@Composable
private fun ButtonColor.textcolorScheme() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.inverseSurface,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.onSecondaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.primaryContainer,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.inverseSurface,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.tertiaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.onSurfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.inversePrimary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.primaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.onPrimary,
        ),
    )

    else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
}

@Composable
private fun ButtonColor.underlinecolorScheme() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.secondary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.secondaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.primaryContainer,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.inverseSurface,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.tertiaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.onSurfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.outline,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colorScheme.errorContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colorScheme.onError,
        ),
    )

    else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
}

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    enabled: Boolean,
    shape: Shape,
    borderColor: Color,
    buttonType: ButtonType,
    onClick: () -> Unit,
    onPressed: (pressed: Boolean) -> Unit,
    keyboardInteractionEnabled: Boolean,
    content: @Composable () -> Unit,
) {
    // FIXME: https://youtrack.jetbrains.com/issue/CMP-6668
    /*    Surface(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            onClick = onClick,
            color = backgroundColor,
            content = content,
        )*/

    val keyboardShow by keyboardAsState()
    val isKeyboardHideButton = keyboardShow && keyboardInteractionEnabled
    val (shapeByKeyboardShow, pressDepth) = if (isKeyboardHideButton) {
        RoundedCornerShape(0.dp) to MIN_PRESS_DEPTH
    } else {
        shape to DEFAULT_PRESS_DEPTH
    }
    val padding = if (isKeyboardHideButton) {
        PaddingValues(
            vertical = 0.dp,
            horizontal = 0.dp,
        )
    } else {
        PaddingValues(
            vertical = 12.dp,
            horizontal = 24.dp,
        )
    }

    Box(
        modifier = modifier
            .modifyIf(keyboardInteractionEnabled) {
                padding(padding)
            }
            .clip(shape = shapeByKeyboardShow)
            .background(color = backgroundColor, shape = shapeByKeyboardShow)
            .clickable(
                pressDepth = pressDepth,
                enabled = enabled,
                onPressed = onPressed,
                onClick = onClick,
            )
            .modifyIf(keyboardInteractionEnabled) {
                imePadding()
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun DmsButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonType: ButtonType,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    buttonColor: ButtonColor,
    contentPadding: PaddingValues? = null,
    keyboardInteractionEnabled: Boolean = false,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val buttoncolorScheme = when (buttonType) {
        ButtonType.Contained -> buttonColor.containedColorScheme()
        ButtonType.Text -> buttonColor.textcolorScheme()
        ButtonType.Underline -> buttonColor.underlinecolorScheme()
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttoncolorScheme.disabled.backgroundColor ?: Color.Transparent
        } else if (pressed) {
            buttoncolorScheme.pressed.backgroundColor ?: Color.Transparent
        } else {
            buttoncolorScheme.enabled.backgroundColor ?: Color.Transparent
        },
    )
    val borderColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttoncolorScheme.disabled.borderColor ?: Color.Transparent
        } else if (pressed) {
            buttoncolorScheme.pressed.borderColor ?: Color.Transparent
        } else {
            buttoncolorScheme.enabled.borderColor ?: Color.Transparent
        },
    )
    val contentColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttoncolorScheme.disabled.textColor
        } else if (pressed) {
            buttoncolorScheme.pressed.textColor
        } else {
            buttoncolorScheme.enabled.textColor
        },
    )
    val innerPadding =
        if (buttonType == ButtonType.Text || buttonType == ButtonType.Underline) {
            PaddingValues(
                horizontal = 8.dp,
                vertical = 6.dp,
            )
        } else {
            PaddingValues(horizontal = 20.dp, vertical = 16.dp)
        }

    // val buttonShape = if (buttonType == ButtonType.Rounded) RoundedCornerShape(24.dp) else shape

    BasicButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        enabled = enabled,
        shape = shape,
        borderColor = borderColor,
        buttonType = buttonType,
        onClick = onClick,
        onPressed = { pressed = it },
        keyboardInteractionEnabled = keyboardInteractionEnabled,
    ) {
        val padding = contentPadding ?: innerPadding
        val textStyle = if (buttonType == ButtonType.Underline || buttonType == ButtonType.Text) {
            DmsTheme.typography.labelM
        } else {
            DmsTheme.typography.bodyM
        }
        val size = with(LocalDensity.current) { textStyle.fontSize.toDp() * 1.2f }
        Box(
            modifier = Modifier
                .padding(padding)
                .height(size),
            contentAlignment = Alignment.Center,
        ) {
            if (isLoading) {
                DmsDotsLoadingIndicator(
                    activeColor = contentColor,
                )
            } else {
                Text(
                    text = text,
                    style = textStyle,
                    color = contentColor,
                    textDecoration = if (buttonType == ButtonType.Underline) TextDecoration.Underline else TextDecoration.None,
                )
            }
        }
    }
}
