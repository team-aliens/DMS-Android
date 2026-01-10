package team.aliens.dms.android.app

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBar
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarVisuals
import team.aliens.dms.android.core.ui.navigation.LocalResultStore
import team.aliens.dms.android.core.ui.navigation.rememberResultStore
import team.aliens.dms.android.data.point.model.PointType
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.main.application.navigation.ApplicationRoute
import team.aliens.dms.android.feature.main.home.navigation.HomeRoute
import team.aliens.dms.android.feature.main.mypage.navigation.MyPageRoute
import team.aliens.dms.android.feature.meal.navigation.MealRoute
import team.aliens.dms.android.feature.notification.navigation.NotificationDetailRoute
import team.aliens.dms.android.feature.notification.navigation.NotificationRoute
import team.aliens.dms.android.feature.onboarding.navigation.OnboardingRoute
import team.aliens.dms.android.feature.point.navigation.PointHistoryRoute
import team.aliens.dms.android.feature.profile.route.AdjustProfileRoute
import team.aliens.dms.android.feature.profile.route.SelectProfileRoute
import team.aliens.dms.android.feature.remain.navigation.RemainApplicationRoute
import team.aliens.dms.android.feature.resetpassword.navigation.CheckPasswordRoute
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordRoute
import team.aliens.dms.android.feature.setting.navigation.SettingRoute
import team.aliens.dms.android.feature.signin.navigation.SignInRoute
import team.aliens.dms.android.feature.vote.navigation.VoteRoute
import java.util.UUID

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
data object VoteScreenNav : NavKey

@Serializable
data object RemainScreenNav : NavKey

@Serializable
data object MyPageScreenNav : NavKey

@Serializable
data object SettingScreenNav : NavKey

@Serializable
data class PointHistoryScreenNav(val pointType: PointType) : NavKey

@Serializable
data object CheckPasswordScreenNav : NavKey

@Serializable
data class ResetPasswordScreenNav(val currentPassword: String) : NavKey

@Serializable
data object SelectProfileScreenNav : NavKey

@Serializable
data class AdjustProfileScreenNav(val model: String) : NavKey

@Serializable
data object NotificationScreenNav : NavKey

@Serializable
data object NotificationDetailScreenNav : NavKey

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    mainViewModel: MainActivityViewModel,
    appState: DmsAppState = rememberDmsAppState(),
) {
    val isJwtAvailable by mainViewModel.autoSignInAvailable.collectAsState()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState()

    val backStack = rememberNavBackStack(OnboardingScreenNav)
    val resultStore = rememberResultStore()
    val currentScreen = backStack.lastOrNull()
    val shouldShowBottomBar = currentScreen in listOf(
        HomeScreenNav,
        ApplicationScreenNav,
        MyPageScreenNav,
    )

    LaunchedEffect(isOnboardingCompleted, isJwtAvailable) {
        val initialScreen = when {
            !isOnboardingCompleted -> OnboardingScreenNav
            isJwtAvailable -> HomeScreenNav
            else -> SignInScreenNav
        }

        if (backStack.lastOrNull() != initialScreen) {
            backStack.clear()
            backStack.add(initialScreen)
        }
    }

    Box {
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
            CompositionLocalProvider(LocalResultStore provides resultStore) {
                NavDisplay(
                    modifier = Modifier
                        .padding(paddingValues),
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
                                onNavigateNotification = {
                                    backStack.add(NotificationScreenNav)
                                },
                                onNavigatePointHistory = {
                                    backStack.add(PointHistoryScreenNav(it))
                                },
                                onNavigateMeal = {
                                    backStack.add(MealScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                },
                            )
                        }
                        entry<ApplicationScreenNav> {
                            ApplicationRoute(
                                onNavigateRemainApplication = {
                                    backStack.add(RemainScreenNav)
                                },
                                onNavigateOutingApplication = {},
                                onNavigateVolunteerApplication = {},
                                onNavigateVote = {
                                    resultStore.setResult<AllVoteSearch?>("vote_result", it)
                                    backStack.add(VoteScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                },
                            )
                        }
                        entry<VoteScreenNav> {
                            VoteRoute(
                                onNavigateBack = { backStack.remove(VoteScreenNav) },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                }
                            )
                        }
                        entry<RemainScreenNav> {
                            RemainApplicationRoute(
                                onNavigateBack = { title ->
                                    resultStore.setResult<String?>("remain_application_result", title)
                                    backStack.remove(RemainScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                }
                            )
                        }
                        entry<MyPageScreenNav> {
                            MyPageRoute(
                                onNavigatePointHistory = {
                                    backStack.add(PointHistoryScreenNav(it))
                                },
                                onNavigateSetting = {
                                    backStack.add(SettingScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                },
                            )
                        }
                        entry<MealScreenNav> {
                            MealRoute(
                                onNavigateBack = { backStack.remove(MealScreenNav) }
                            )
                        }
                        entry<SettingScreenNav> {
                            SettingRoute(
                                onBackPressed = { backStack.remove(SettingScreenNav) },
                                onNavigateResetPassword = { backStack.add(CheckPasswordScreenNav) },
                                onNavigateSelectProfile = { backStack.add(SelectProfileScreenNav) },
                                onNavigateSignIn = {
                                    backStack.clear()
                                    backStack.add(SignInScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                }
                            )
                        }
                        entry<PointHistoryScreenNav> {
                            PointHistoryRoute(
                                onBackClick = { backStack.remove(PointHistoryScreenNav(it.pointType)) }
                            )
                        }
                        entry<ResetPasswordScreenNav> {
                            ResetPasswordRoute(
                                onBackPressed = { backStack.removeLastOrNull() },
                                currentPassword = it.currentPassword,
                                onNavigateSetting = {
                                    backStack.removeLastOrNull()
                                    backStack.remove(CheckPasswordScreenNav)
                                },
                                onShowSnackBar = { snackBar, message ->
                                    appState.showSnackBar(snackBar, message)
                                },
                            )
                        }
                        entry<CheckPasswordScreenNav> {
                            CheckPasswordRoute(
                                onBackPressed = { backStack.remove(CheckPasswordScreenNav) },
                                onNavigateResetPassword = { backStack.add(ResetPasswordScreenNav(it)) },
                                onShowSnackBar = { snackBar, message ->
                                    appState.showSnackBar(snackBar, message)
                                }
                            )
                        }
                        entry<SelectProfileScreenNav> {
                            SelectProfileRoute(
                                onBackPressed = { backStack.remove(SelectProfileScreenNav) },
                                onNavigateAdjustProfile = { backStack.add(AdjustProfileScreenNav(model = it)) },
                                onShowSnackBar = { snackBar, message ->
                                    appState.showSnackBar(snackBar, message)
                                }
                            )
                        }
                        entry<AdjustProfileScreenNav> {
                            AdjustProfileRoute(
                                onBackPressed = { backStack.remove(AdjustProfileScreenNav(it.model)) },
                                model = it.model,
                                onShowSnackBar = { snackBar, message ->
                                    appState.showSnackBar(snackBar, message)
                                }
                            )
                        }
                        entry<NotificationScreenNav> {
                            NotificationRoute(
                                onBackClick = { backStack.remove(NotificationScreenNav) },
                                onNavigateNotificationDetailClick = {
                                    resultStore.setResult<UUID?>("notice_result", it)
                                    backStack.add(NotificationDetailScreenNav)
                                },
                                onNavigatePointHistory = { pointValue ->
                                    backStack.add(PointHistoryScreenNav(pointValue))
                                }
                            )
                        }
                        entry<NotificationDetailScreenNav> {
                            NotificationDetailRoute(
                                onNavigateBack = {
                                    backStack.remove(NotificationDetailScreenNav)
                                },
                                onShowSnackBar = { snackBarType, message ->
                                    appState.showSnackBar(snackBarType, message)
                                },
                            )
                        }
                    },
                )
            }
            SnackbarHost(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 16.dp)
                    .align(Alignment.TopCenter),
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
