package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.EnterEmailVerificationCodeScreen

@Composable
fun SignUpEnterEmailVerificationCodeRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    EnterEmailVerificationCodeScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToEnterStudentNumber = navigateToEnterStudentNumber,
        onShowSnackBar = onShowSnackBar,
    )
}
