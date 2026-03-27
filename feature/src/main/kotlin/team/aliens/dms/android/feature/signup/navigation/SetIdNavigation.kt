package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.SetIdScreen

@Composable
fun SignUpSetIdRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    SetIdScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToSetPassword = navigateToSetPassword,
        onShowSnackBar = onShowSnackBar,
    )
}
