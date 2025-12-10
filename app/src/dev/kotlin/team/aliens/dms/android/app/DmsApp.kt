package team.aliens.dms.android.app

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.onboarding.navigation.OnboardingRoute
import team.aliens.dms.android.feature.signin.navigation.SignInRoute

@Serializable
data object OnboardingScreenNav : NavKey

@Serializable
data object SignInScreenNav : NavKey

@Serializable
data object MainScreenNav : NavKey

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    isJwtAvailable: StateFlow<Boolean>,
    mainViewModel: MainActivityViewModel,
) {
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()
    val isJwtAvailableState by isJwtAvailable.collectAsState()

    val backStack = rememberNavBackStack(OnboardingScreenNav)

    LaunchedEffect(isOnboardingCompleted, isJwtAvailableState) {
        val initialScreen = when {
            !isOnboardingCompleted -> OnboardingScreenNav
            isJwtAvailableState -> MainScreenNav
            else -> SignInScreenNav
        }

        if (backStack.lastOrNull() != initialScreen) {
            backStack.clear()
            backStack.add(initialScreen)
        }
    }

    NavDisplay(
        modifier = Modifier.systemBarsPadding(),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<OnboardingScreenNav> {
                OnboardingRoute(
                    navigateToSignIn = { backStack.add(SignInScreenNav) },
                )
            }
            entry<SignInScreenNav> {
                SignInRoute()
            }
            entry<MainScreenNav> {
                Text(
                    text = "Main Screen (TODO)",
                    color = DmsTheme.colorScheme.onSurface,
                )
            }
        },
    )
}
