package team.aliens.dms.android.feature.editpassword.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.editpassword.ui.CheckPassword

@Composable
fun CheckPasswordRoute(
    onBackPressed: () -> Unit,
    onNavigateEditPassword: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    CheckPassword(
        onBackPressed = onBackPressed,
        onNavigateEditPassword = onNavigateEditPassword,
        onShowSnackBar = onShowSnackBar,
    )
}