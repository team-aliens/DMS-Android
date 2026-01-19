package team.aliens.dms.android.feature.notification.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.notification.ui.NotificationScreen
import java.util.UUID

@Composable
fun NotificationRoute(
    onBackClick: () -> Unit,
    onNavigateNotificationDetailClick: (UUID) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    NotificationScreen(
        onBackClick = onBackClick,
        onNavigateNotificationDetailClick = onNavigateNotificationDetailClick,
        onNavigatePointHistory = onNavigatePointHistory,
        onShowSnackBar = onShowSnackBar
    )
}
