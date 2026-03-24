package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.EnterSchoolVerificationCodeScreen

@Composable
fun SignUpEnterSchoolVerificationCodeRoute(
    onBack: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    EnterSchoolVerificationCodeScreen(
        onBack = onBack,
        navigateToEnterSchoolVerificationQuestion = navigateToEnterSchoolVerificationQuestion,
        onShowSnackBar = onShowSnackBar,
    )
}
