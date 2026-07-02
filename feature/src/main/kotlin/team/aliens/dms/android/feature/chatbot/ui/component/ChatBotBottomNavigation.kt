package team.aliens.dms.android.feature.chatbot.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme

@Composable
internal fun ChatBotBottomNavigation(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        color = DmsTheme.colorScheme.surfaceTint,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChatBotBottomNavigationItem("⌂", "홈", false)
            ChatBotBottomNavigationItem("✓", "신청", false)
            ChatBotBottomNavigationItem("▱", "챗봇", true)
            ChatBotBottomNavigationItem("♙", "마이페이지", false)
        }
    }
}

@Composable
private fun ChatBotBottomNavigationItem(
    icon: String,
    label: String,
    selected: Boolean,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            color = if (selected) {
                DmsTheme.colorScheme.onBackground
            } else {
                DmsTheme.colorScheme.surfaceTint
            },
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = icon,
                    color = if (selected) {
                        DmsTheme.colorScheme.surfaceTint
                    } else {
                        DmsTheme.colorScheme.onSurfaceVariant
                    },
                    style = DmsTheme.typography.body1,
                )
            }
        }

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = label,
            color = if (selected) {
                DmsTheme.colorScheme.onBackground
            } else {
                DmsTheme.colorScheme.onSurfaceVariant
            },
            style = DmsTheme.typography.caption,
        )
    }
}
