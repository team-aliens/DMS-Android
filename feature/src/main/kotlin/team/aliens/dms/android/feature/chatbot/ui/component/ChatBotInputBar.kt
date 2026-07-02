package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotInputBar(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(14.dp, RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 24.dp,
                top = 10.dp,
                end = 10.dp,
                bottom = 10.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "DMS AI에게 질문해 보세요...",
                color = DmsTheme.colorScheme.onSurfaceVariant,
                style = DmsTheme.typography.body2,
            )

            Spacer(modifier = Modifier.weight(1f))

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = DmsTheme.colorScheme.secondary,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "↑",
                        color = DmsTheme.colorScheme.surfaceTint,
                        style = DmsTheme.typography.title2,
                    )
                }
            }
        }
    }
}
