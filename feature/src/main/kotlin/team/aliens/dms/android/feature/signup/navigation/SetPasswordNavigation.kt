package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.signup.model.SignUpData
import team.aliens.dms.android.feature.signup.ui.SetPasswordScreen

@Composable
fun SignUpSetPasswordRoute(
    signUpData: SignUpData,
    onBack: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
) {
    SetPasswordScreen(
        signUpData = signUpData,
        onBack = onBack,
        navigateToTerms = navigateToTerms,
    )
}
