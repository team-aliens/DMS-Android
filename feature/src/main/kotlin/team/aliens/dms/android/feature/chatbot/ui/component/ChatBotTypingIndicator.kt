package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotTypingIndicator() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(DmsTheme.colorScheme.surface)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(DmsTheme.colorScheme.inverseSurface),
            )
        }
    }
}
