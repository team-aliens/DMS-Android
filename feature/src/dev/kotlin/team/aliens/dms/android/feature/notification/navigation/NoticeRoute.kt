package team.aliens.dms.android.feature.notification.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.notification.ui.Notices
import java.util.UUID

@Composable
fun NoticeRoute(
    onNavigateBack: () -> Unit,
    onNoticeDetailClick: (UUID) -> Unit
) {
    Notices(
        onNavigateBack = onNavigateBack,
        onNoticeDetailClick = onNoticeDetailClick,
    )
}
