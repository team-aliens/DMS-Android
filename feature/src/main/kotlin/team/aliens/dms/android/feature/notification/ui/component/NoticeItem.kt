package team.aliens.dms.android.feature.notification.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.bodyM
import team.aliens.dms.android.core.designsystem.foundation.DmsIcon
import team.aliens.dms.android.core.designsystem.labelM
import team.aliens.dms.android.core.designsystem.startPadding
import team.aliens.dms.android.core.designsystem.topPadding
import team.aliens.dms.android.core.designsystem.util.clickable
import team.aliens.dms.android.feature.notification.viewmodel.NotificationUi
import java.util.UUID

@Composable
internal fun NoticeItem(
    notice: NotificationUi,
    onNotificationDetailClick: (UUID, UUID) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onNotificationDetailClick(notice.linkId, notice.id) })
                .padding(horizontal = 24.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(DmsIcon.Notice),
                tint = DmsTheme.colorScheme.scrim,
                contentDescription = null,
            )
            Column(
                modifier = Modifier.startPadding(12.dp),
            ) {
                Text(
                    text = notice.title,
                    style = DmsTheme.typography.bodyM,
                )
                Row(
                    modifier = Modifier.topPadding(6.dp)
                ) {
                    if (!notice.isRead) {
                        Icon(
                            modifier = Modifier.size(4.dp),
                            imageVector = Icons.Filled.Circle,
                            contentDescription = null,
                            tint = DmsTheme.colorScheme.primaryContainer,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .startPadding(4.dp),
                        text = notice.content,
                        style = DmsTheme.typography.labelM,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = notice.elapsedText,
                style = DmsTheme.typography.bodyM,
                color = DmsTheme.colorScheme.inverseSurface,
            )
            Icon(
                painter = painterResource(DmsIcon.Forward),
                tint = DmsTheme.colorScheme.scrim,
                contentDescription = null,
            )
        }
    }
}
