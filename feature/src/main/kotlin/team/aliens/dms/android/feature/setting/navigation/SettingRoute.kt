package team.aliens.dms.android.feature.setting.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.setting.ui.Setting

@Composable
fun SettingRoute(
    onBackPressed: () -> Unit,
    onNavigateEditPassword: () -> Unit,
    onNavigateSelectProfile: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    Setting(
        onBackPressed = onBackPressed,
        onNavigateEditPassword = onNavigateEditPassword,
        onNavigateSelectProfile = onNavigateSelectProfile,
        onNavigateSignIn = onNavigateSignIn,
        onShowSnackBar = onShowSnackBar
    )
}
