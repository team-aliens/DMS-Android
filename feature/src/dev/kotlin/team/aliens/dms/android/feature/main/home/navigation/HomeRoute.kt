package team.aliens.dms.android.feature.main.home.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.feature.main.home.ui.Home

@Composable
fun HomeRoute() {
    Home(
        onNavigateNotice = { },
        onNavigateNoticeDetail = { },
        onNavigatePointHistory = { },
        onNavigateMeal = { },
        onShowSnackBar = { _, _ -> },
    )
}
