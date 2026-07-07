package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import kotlin.math.roundToInt

@Composable
internal fun ChatBotTypingIndicator() {
    Row(
        modifier = Modifier.padding(start = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        repeat(3) { index ->
            ChatBotTypingDot(
                delayMillis = index * 120,
            )
        }
    }
}

@Composable
private fun ChatBotTypingDot(
    delayMillis: Int,
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "ChatBotTypingDotTransition",
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -4f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 720
                0f at delayMillis
                -4f at delayMillis + 180
                0f at delayMillis + 360
                0f at 720
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "ChatBotTypingDotOffset",
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 720
                0.35f at delayMillis
                1f at delayMillis + 180
                0.35f at delayMillis + 360
                0.35f at 720
            },
            repeatMode = RepeatMode.Restart,
        ),
        label = "ChatBotTypingDotAlpha",
    )

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = offsetY.roundToInt(),
                )
            }
            .size(6.dp)
            .alpha(alpha)
            .clip(CircleShape)
            .background(DmsTheme.colorScheme.inverseSurface),
    )
}
