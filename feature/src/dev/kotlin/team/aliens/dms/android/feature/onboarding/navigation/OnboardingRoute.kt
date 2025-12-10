package team.aliens.dms.android.feature.onboarding.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.feature.onboarding.ui.OnboardingScreen

@Composable
fun OnboardingRoute(
    navigateToSignIn: () -> Unit,
) {
    OnboardingScreen(
        navigateToSignIn = navigateToSignIn
    )
}
