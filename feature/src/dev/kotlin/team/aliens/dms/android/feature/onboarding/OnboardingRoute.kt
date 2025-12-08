package team.aliens.dms.android.feature.onboarding

import androidx.compose.runtime.Composable

@Composable
fun OnboardingRoute(
    onComplete: () -> Unit,
) {
    OnboardingScreen(
        onComplete = onComplete,
    )
}