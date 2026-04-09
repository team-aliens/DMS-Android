package team.aliens.dms.android.app.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class DmsAppRootDestinationTest {

    @Test
    fun onboardingIncomplete_routesToOnboarding() {
        assertEquals(
            RootDestination.ONBOARDING,
            resolveRootDestination(
                isOnboardingCompleted = false,
                isJwtAvailable = false,
            ),
        )
    }

    @Test
    fun authenticatedUser_routesToHome() {
        assertEquals(
            RootDestination.HOME,
            resolveRootDestination(
                isOnboardingCompleted = true,
                isJwtAvailable = true,
            ),
        )
    }

    @Test
    fun expiredSession_routesToSignIn() {
        assertEquals(
            RootDestination.SIGN_IN,
            resolveRootDestination(
                isOnboardingCompleted = true,
                isJwtAvailable = false,
            ),
        )
    }
}
