package team.aliens.dms.android.feature.setting.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.setting.ui.Setting

@Composable
fun SettingRoute(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    Setting(
        onBackPressed = onBackPressed,
        onNavigateResetPassword = onNavigateResetPassword,
        onShowSnackBar = onShowSnackBar
    )
}
