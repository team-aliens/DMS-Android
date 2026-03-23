package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.EnterEmailScreen

@Composable
fun SignUpEnterEmailRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    EnterEmailScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToEnterEmailVerificationCode = navigateToEnterEmailVerificationCode,
        onShowSnackBar = onShowSnackBar,
    )
}
