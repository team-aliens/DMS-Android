package team.aliens.dms.android.feature.editpassword.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.editpassword.ui.EditPassword


@Composable
fun EditPasswordRoute(
    onBack: () -> Unit,
    currentPassword: String,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    EditPassword(
        onBack = onBack,
        currentPassword = currentPassword,
        onNavigateSetting = onNavigateSetting,
        onShowSnackBar = onShowSnackBar,
    )
}