package team.aliens.dms.android.feature.resetpassword.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.resetpassword.ui.ResetPassword


@Composable
fun ResetPasswordRoute(
    onBackPressed: () -> Unit,
    currentPassword: String,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    ResetPassword(
        onBackPressed = onBackPressed,
        currentPassword = currentPassword,
        onNavigateSetting = onNavigateSetting,
        onShowSnackBar = onShowSnackBar,
    )
}
