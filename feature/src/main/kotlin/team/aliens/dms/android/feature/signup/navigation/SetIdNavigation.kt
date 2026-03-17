package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.SetIdScreen

@Composable
fun SignUpSetIdRoute(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    SetIdScreen(
        signUpData = signUpData,
        onBackPressed = onBackPressed,
        navigateToSetPassword = navigateToSetPassword,
        onShowSnackBar = onShowSnackBar,
    )
}
