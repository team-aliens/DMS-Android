package team.aliens.dms.android.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.window.layout.DisplayFeature
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScopeWithNoDependencies
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.NavHostEngine
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.android.app.navigation.DmsNavGraph
import team.aliens.dms.android.app.navigation.DmsNavigator
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph
import team.aliens.dms.android.feature.destinations.TermsScreenDestination
import team.aliens.dms.android.feature.editpassword.EditPasswordViewModel
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.resetpassword.ResetPasswordViewModel
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.SignUpViewModel
import team.aliens.dms.android.feature.signup.TermsUrl
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph
import team.aliens.dms.android.network.BuildConfig

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    engine: NavHostEngine = rememberDmsNavHostEngine(),
    isJwtAvailable: StateFlow<Boolean>,
    appState: DmsAppState = rememberDmsAppState(
        navController = engine.rememberNavController(),
        isJwtAvailable = isJwtAvailable,
    ),
) {
    val autoSignIn by appState.isJwtAvailable.collectAsStateWithLifecycle()
    DestinationsNavHost(
        engine = engine,
        navGraph = DmsNavGraph.root(autoSignIn = autoSignIn),
        navController = appState.navController,
        dependenciesContainerBuilder = {
            dependency(currentNavigator(autoSignIn = autoSignIn))

            dependency(SignUpNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(SignUpNavGraph.route)
                }
                hiltViewModel<SignUpViewModel>(parentEntry)
            }

            dependency(TermsScreenDestination) { TermsUrl(value = BuildConfig.TERMS_URL) }

            dependency(EditPasswordNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(EditPasswordNavGraph.route)
                }
                hiltViewModel<EditPasswordViewModel>(parentEntry)
            }

            dependency(ResetPasswordNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(ResetPasswordNavGraph.route)
                }
                hiltViewModel<ResetPasswordViewModel>(parentEntry)
            }
        },
    )
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun rememberDmsNavHostEngine() = rememberAnimatedNavHostEngine(
    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
    defaultAnimationsForNestedNavGraph = mapOf(
        AuthorizedNavGraph to NestedNavGraphDefaultAnimations(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { fadeOut(tween(delayMillis = 10)) },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(delayMillis = 10),
                )
            },
        ), UnauthorizedNavGraph to NestedNavGraphDefaultAnimations(
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start) },
            exitTransition = { fadeOut(tween(delayMillis = 10)) },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(delayMillis = 10),
                )
            },
        )
    ),
)

private fun DestinationScopeWithNoDependencies<*>.currentNavigator(autoSignIn: Boolean): DmsNavigator =
    DmsNavigator(
        navGraph = navBackStackEntry.destination.navGraph(autoSignIn),
        navController = navController,
    )

private fun NavDestination.navGraph(autoSignIn: Boolean): NavGraphSpec {
    hierarchy.forEach { destination ->
        DmsNavGraph.root(autoSignIn).nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }

    throw RuntimeException("Unknown nav graph for destination $route")
}
