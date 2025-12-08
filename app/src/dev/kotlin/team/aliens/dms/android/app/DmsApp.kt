package team.aliens.dms.android.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.feature.onboarding.OnboardingRoute

@Serializable
data object OnboardingScreenNav : NavKey

@Serializable
data object LoginScreenNav : NavKey

@Serializable
data object MainScreenNav : NavKey

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    isJwtAvailable: StateFlow<Boolean>,
    mainViewModel: MainActivityViewModel,
) {
    val isUpdateFailed by mainViewModel.isUpdateFailed.collectAsState()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()
    val isJwtAvailableState by isJwtAvailable.collectAsState()
    val toast = LocalToast.current


    // TODO :: 변수 없이 바로 처리 가능하게
    val initialScreen = when {
        isOnboardingCompleted == false -> OnboardingScreenNav  // 온보딩 미완료
        isJwtAvailableState -> MainScreenNav                   // 온보딩 완료 + JWT 있음
        else -> LoginScreenNav                                // 온보딩 완료 + JWT 없음
    }

    val backStack = rememberNavBackStack(initialScreen)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<OnboardingScreenNav> {
                    OnboardingRoute(
                        onComplete = {
                            mainViewModel.completeOnboarding()
                            backStack.clear()
                            backStack.add(
                                if (isJwtAvailableState) MainScreenNav else LoginScreenNav
                            )
                        }
                    )
                }
                entry<LoginScreenNav> {
                    Text(
                        text = "Login Screen (TODO)",
                        color = DmsTheme.colorScheme.onSurface,
                    )
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
}
