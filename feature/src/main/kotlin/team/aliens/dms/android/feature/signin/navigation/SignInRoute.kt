package team.aliens.dms.android.feature.signin.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signin.ui.SignIn

@Composable
fun SignInRoute(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    SignIn(
        navigateToMain = navigateToMain,
        navigateToSignUp = navigateToSignUp,
        navigateToFindId = navigateToFindId,
        navigateToFindPassword = navigateToResetPassword,
        onShowSnackBar = onShowSnackBar,
    )
}
