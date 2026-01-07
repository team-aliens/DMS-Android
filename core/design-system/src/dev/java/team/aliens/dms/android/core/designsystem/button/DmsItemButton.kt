package team.aliens.dms.android.core.designsystem.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.util.clickable

@Composable
fun DmsItemButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (!enabled) {
            DmsTheme.colorScheme.onSurface
        } else if (pressed) {
            DmsTheme.colorScheme.surfaceVariant
        } else {
            DmsTheme.colorScheme.surfaceTint
        },
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(color = backgroundColor, shape = RoundedCornerShape(32.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick,
                onPressed = { pressed = it },
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = DmsTheme.typography.bodyB,
            color = DmsTheme.colorScheme.inverseOnSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colorScheme.scrim,
            contentDescription = null,
        )
    }
}
