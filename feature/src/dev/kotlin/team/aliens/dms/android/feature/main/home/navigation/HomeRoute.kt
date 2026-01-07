package team.aliens.dms.android.feature.main.home.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.feature.main.home.ui.Home

@Composable
fun HomeRoute(
    onNavigateNotification: () -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    Home(
        onNavigateNotification = onNavigateNotification,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateMeal = onNavigateMeal,
        onShowSnackBar = onShowSnackBar,
    )
}
