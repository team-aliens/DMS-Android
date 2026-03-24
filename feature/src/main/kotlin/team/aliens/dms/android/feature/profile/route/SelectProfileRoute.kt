package team.aliens.dms.android.feature.profile.route

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.profile.ui.SelectProfile

@Composable
fun SelectProfileRoute(
    onBack: () -> Unit,
    onNavigateAdjustProfile: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    SelectProfile(
        onBack = onBack,
        onNavigateAdjustProfile = onNavigateAdjustProfile,
        onShowSnackBar = onShowSnackBar,
    )
}
