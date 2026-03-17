package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.EnterSchoolVerificationQuestionScreen

@Composable
fun SignUpEnterSchoolVerificationQuestionRoute(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    EnterSchoolVerificationQuestionScreen(
        signUpData = signUpData,
        onBackPressed = onBackPressed,
        navigateToEnterEmail = navigateToEnterEmail,
        onShowSnackBar = onShowSnackBar,
    )
}
