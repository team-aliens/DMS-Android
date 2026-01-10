package team.aliens.dms.android.feature.notification.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.notification.ui.NotificationDetail

@Composable
fun NotificationDetailRoute(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    NotificationDetail(
        onNavigateBack = onNavigateBack,
        onShowSnackBar = onShowSnackBar,
    )
}
