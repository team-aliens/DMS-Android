package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.TermsScreen

@Composable
fun SignUpTermsRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToComplete: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    TermsScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToComplete = navigateToComplete,
        onShowSnackBar = onShowSnackBar,
    )
}
