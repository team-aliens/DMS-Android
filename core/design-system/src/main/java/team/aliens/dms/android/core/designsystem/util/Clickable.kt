package team.aliens.dms.android.core.designsystem.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal const val DURATION_MILLIS = 100
internal const val DEFAULT_PRESS_DEPTH = 0.98f
internal const val MIN_PRESS_DEPTH = 1f
internal const val DEFAULT_DISABLED_MILLIS = 300L

@Composable
fun Modifier.clickable(
    onClick: () -> Unit,
    enabled: Boolean = true,
    pressDepth: Float = DEFAULT_PRESS_DEPTH,
    indication: Indication? = ripple(),
    isSingleClick: Boolean = true,
    onPress: ((pressed: Boolean) -> Unit)? = null,
    disabledMillis: Long = DEFAULT_DISABLED_MILLIS,
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isClickable by remember { mutableStateOf(true) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            pressDepth
        } else {
            1f
        },
        animationSpec = tween(delayMillis = DURATION_MILLIS),
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isPressed) {
        onPress?.invoke(isPressed)
    }

    return this
        .clickable(
            enabled = enabled && isClickable,
            indication = indication,
            interactionSource = interactionSource,
        ) {
            if (isSingleClick) {
                isClickable = false
                onClick()
                coroutineScope.launch {
                    delay(disabledMillis)
                    isClickable = true
                }
            } else {
                onClick()
            }
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
        )
}
