package team.aliens.dms.android.feature.notification.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.notification.ui.Notices
import java.util.UUID

@Composable
fun NoticeRoute(
    onBackClick: () -> Unit,
    onNavigateNoticeDetailClick: (UUID) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
) {
    Notices(
        onBackClick = onBackClick,
        onNavigateNoticeDetailClick = onNavigateNoticeDetailClick,
        onNavigatePointHistory = onNavigatePointHistory,
    )
}
