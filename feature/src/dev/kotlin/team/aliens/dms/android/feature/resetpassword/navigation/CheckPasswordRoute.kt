package team.aliens.dms.android.feature.resetpassword.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.resetpassword.ui.CheckPassword

@Composable
fun CheckPasswordRoute(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    CheckPassword(
        onBackPressed = onBackPressed,
        onNavigateResetPassword = onNavigateResetPassword,
        onShowSnackBar = onShowSnackBar,
    )
}
