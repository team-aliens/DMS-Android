package team.aliens.dms.android.feature.main.home.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.main.home.ui.Home

@Composable
fun HomeRoute(
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    Home(
        onNavigateNotice = { },
        onNavigateNoticeDetail = { },
        onNavigatePointHistory = { },
        onNavigateMeal = onNavigateMeal,
        onShowSnackBar = onShowSnackBar,
    )
}
