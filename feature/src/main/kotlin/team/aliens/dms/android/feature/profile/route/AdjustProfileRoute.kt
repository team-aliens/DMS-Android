package team.aliens.dms.android.feature.profile.route

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.profile.ui.AdjustProfile
import team.aliens.dms.android.feature.profile.ui.SelectProfile

@Composable
fun AdjustProfileRoute(
    onBackPressed: () -> Unit,
    model: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    AdjustProfile(
        onBackPressed = onBackPressed,
        model = model,
        onShowSnackBar = onShowSnackBar,
    )
}
