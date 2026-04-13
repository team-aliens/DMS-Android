package team.aliens.dms.android.feature.latestudy.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyB

@Composable
fun LateStudyCalendarSection(
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Text(
            text = "일정",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = DmsTheme.colorScheme.onBackground,
            style = DmsTheme.typography.bodyB,
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "(새벽 자습은 금, 토, 일요일은 불가능합니다)",
                color = DmsTheme.colorScheme.onSurfaceVariant,
                style = DmsTheme.typography.caption,
            )
        }

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "이전 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Text(
                text = "2026 4월",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "다음 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
