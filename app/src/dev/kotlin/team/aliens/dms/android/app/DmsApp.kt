package team.aliens.dms.android.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBar
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarVisuals
import team.aliens.dms.android.feature.main.application.navigation.ApplicationRoute
import team.aliens.dms.android.feature.main.home.navigation.HomeRoute
import team.aliens.dms.android.feature.main.mypage.navigation.MyPageRoute
import team.aliens.dms.android.feature.meal.navigation.MealRoute
import team.aliens.dms.android.feature.onboarding.navigation.OnboardingRoute
import team.aliens.dms.android.feature.signin.navigation.SignInRoute

@Serializable
data object OnboardingScreenNav : NavKey

@Serializable
data object SignInScreenNav : NavKey

@Serializable
data object HomeScreenNav : NavKey

@Serializable
data object MealScreenNav : NavKey

@Serializable
data object ApplicationScreenNav : NavKey

@Serializable
data object MyPageScreenNav : NavKey

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    isJwtAvailable: StateFlow<Boolean>,
    mainViewModel: MainActivityViewModel,
    appState: DmsAppState = rememberDmsAppState(),
) {
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()
    val isJwtAvailableState by isJwtAvailable.collectAsState()

    val backStack = rememberNavBackStack(OnboardingScreenNav)
    val currentScreen = backStack.lastOrNull()
    val shouldShowBottomBar = currentScreen in listOf(
        HomeScreenNav,
        ApplicationScreenNav,
        MyPageScreenNav,
    )

    LaunchedEffect(isOnboardingCompleted, isJwtAvailableState) {
        val initialScreen = when {
            !isOnboardingCompleted -> OnboardingScreenNav
            isJwtAvailableState -> HomeScreenNav
            else -> SignInScreenNav
        }

        if (backStack.lastOrNull() != initialScreen) {
            backStack.clear()
            backStack.add(initialScreen)
        }
    }

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavigate = { destination ->
                        if (currentScreen != destination) {
                            backStack.removeAll {
                                it is HomeScreenNav ||
                                it is ApplicationScreenNav ||
                                it is MyPageScreenNav
                            }
                            backStack.add(destination)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavDisplay(
            modifier = Modifier
                .padding(paddingValues)
                .navigationBarsPadding(),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<OnboardingScreenNav> {
                    OnboardingRoute(
                        navigateToSignIn = {
                            backStack.clear()
                            backStack.add(SignInScreenNav)
                        },
                    )
                }
                entry<SignInScreenNav> {
                    SignInRoute(
                        navigateToMain = {
                            backStack.clear()
                            backStack.add(HomeScreenNav)
                        },
                        navigateToSignUp = {},
                        onShowSnackBar = { snackBarType, message ->
                            appState.showSnackBar(snackBarType, message)
                        },
                    )
                }
                entry<HomeScreenNav> {
                    HomeRoute(
                        onNavigateMeal = {
                            backStack.add(MealScreenNav)
                        }
                    )
                }
                entry<ApplicationScreenNav> {
                    ApplicationRoute()
                }
                entry<MyPageScreenNav> {
                    MyPageRoute()
                }
                entry<MealScreenNav> {
                    MealRoute(
                        onNavigateBack = { backStack.removeLastOrNull() }
                    )
                }
            },
        )
        Box {
            SnackbarHost(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .align(Alignment.Center),
                hostState = appState.snackBarHostState,
                snackbar = {
                    val visuals = it.visuals as? DmsSnackBarVisuals ?: return@SnackbarHost
                    DmsSnackBar(
                        snackBarType = visuals.snackBarType,
                        message = visuals.message,
                    )
                },
            )
        }
    }
}
