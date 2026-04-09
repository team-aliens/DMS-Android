package team.aliens.dms.android.app.ui

internal enum class RootDestination {
    ONBOARDING,
    HOME,
    SIGN_IN,
}

internal fun resolveRootDestination(
    isOnboardingCompleted: Boolean,
    isJwtAvailable: Boolean,
): RootDestination = when {
    !isOnboardingCompleted -> RootDestination.ONBOARDING
    isJwtAvailable -> RootDestination.HOME
    else -> RootDestination.SIGN_IN
}
