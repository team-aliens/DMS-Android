package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.EnterStudentNumberScreen

@Composable
fun SignUpEnterStudentNumberRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    EnterStudentNumberScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToSetId = navigateToSetId,
        onShowSnackBar = onShowSnackBar,
    )
}
