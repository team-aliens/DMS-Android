package team.aliens.dms.android.feature.notice.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.notice.ui.NoticeDetail

@Composable
fun NoticeDetailRoute(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    NoticeDetail(
        onNavigateBack = onNavigateBack,
        onShowSnackBar = onShowSnackBar,
    )
}
