package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.SetPasswordScreen

@Composable
fun SignUpSetPasswordRoute(
    signUpData: SignUpData,
    onBackPressed: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
) {
    SetPasswordScreen(
        signUpData = signUpData,
        onBackPressed = onBackPressed,
        navigateToTerms = navigateToTerms,
    )
}
