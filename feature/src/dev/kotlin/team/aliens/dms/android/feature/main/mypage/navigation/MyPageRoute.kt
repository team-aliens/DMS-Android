package team.aliens.dms.android.feature.main.mypage.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.main.mypage.ui.MyPage

@Composable
fun MyPageRoute(
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateSetting: () -> Unit,
    onNavigateNotification: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    MyPage(
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateSetting = onNavigateSetting,
        onNavigateNotification = onNavigateNotification,
        onShowSnackBar = onShowSnackBar,
    )
}
