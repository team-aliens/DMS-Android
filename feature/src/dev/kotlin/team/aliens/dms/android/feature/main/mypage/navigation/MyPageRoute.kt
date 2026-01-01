package team.aliens.dms.android.feature.main.mypage.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.Point
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.main.mypage.ui.MyPage

@Composable
fun MyPageRoute(
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    MyPage(
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateSetting = onNavigateSetting,
        onShowSnackBar = onShowSnackBar,
    )
}
