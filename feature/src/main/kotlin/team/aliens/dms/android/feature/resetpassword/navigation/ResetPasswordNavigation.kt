package team.aliens.dms.android.feature.resetpassword.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.resetpassword.ResetPasswordScreen

@Composable
fun ResetPasswordRoute(
    onNavigateToBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    ResetPasswordScreen(
        onNavigateBack = onNavigateToBack,
        onShowSnackBar = onShowSnackBar,
    )
}
