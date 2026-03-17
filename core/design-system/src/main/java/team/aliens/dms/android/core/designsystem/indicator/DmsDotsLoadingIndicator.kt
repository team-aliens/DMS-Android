package team.aliens.dms.android.core.designsystem.indicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

@Composable
fun DmsDotsLoadingIndicator(
    modifier: Modifier = Modifier,
    dotCount: Int = 3,
    dotSize: Dp = 6.dp,
    activeColor: Color = Color.White,
    baseColor: Color = activeColor.copy(alpha = 0.5f),
    durationMillis: Int = 1000,
) {
    val transition = rememberInfiniteTransition()
    val globalProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
        ),
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(dotCount) { index ->
            val phaseOffset = index * (2 * PI / dotCount)
            val wave = abs(sin((globalProgress * 2 * PI) - phaseOffset)).toFloat()

            val scale = 1f + 0.4f * wave
            val color = lerp(baseColor, activeColor, wave)

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .scale(scale)
                    .background(color, CircleShape),
            )
        }
    }
}
