package team.aliens.dms.android.feature.signup.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.signup.ui.CompleteScreen

@Composable
fun SignUpCompleteRoute(
    navigateToSignIn: () -> Unit,
) {
    CompleteScreen(
        navigateToSignIn = navigateToSignIn,
    )
}
