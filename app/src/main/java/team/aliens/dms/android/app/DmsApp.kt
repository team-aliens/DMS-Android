package team.aliens.dms.android.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.window.layout.DisplayFeature
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScopeWithNoDependencies
import com.ramcosta.composedestinations.spec.NavGraphSpec
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.android.app.navigation.DmsNavGraph
import team.aliens.dms.android.app.navigation.DmsNavigator
import team.aliens.dms.android.feature.destinations.TermsScreenDestination
import team.aliens.dms.android.feature.editpassword.EditPasswordViewModel
import team.aliens.dms.android.feature.editpassword.navigation.EditPasswordNavGraph
import team.aliens.dms.android.feature.outing.OutingViewModel
import team.aliens.dms.android.feature.outing.navigation.OutingNavGraph
import team.aliens.dms.android.feature.resetpassword.ResetPasswordViewModel
import team.aliens.dms.android.feature.resetpassword.navigation.ResetPasswordNavGraph
import team.aliens.dms.android.feature.signup.SignUpViewModel
import team.aliens.dms.android.feature.signup.TermsUrl
import team.aliens.dms.android.feature.signup.navigation.SignUpNavGraph
import team.aliens.dms.android.feature.voting.VotingViewModel
import team.aliens.dms.android.feature.voting.navigation.VotingNavGraph
import team.aliens.dms.android.network.BuildConfig

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    isJwtAvailable: StateFlow<Boolean>,
    appState: DmsAppState = rememberDmsAppState(
        isJwtAvailable = isJwtAvailable,
    ),
) {
    val autoSignIn by appState.isJwtAvailable.collectAsStateWithLifecycle()
    DestinationsNavHost(
        engine = appState.engine,
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

            dependency(OutingNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(OutingNavGraph.route)
                }
                hiltViewModel<OutingViewModel>(parentEntry)
            }

            dependency(ResetPasswordNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(ResetPasswordNavGraph.route)
                }
                hiltViewModel<ResetPasswordViewModel>(parentEntry)
            }

            dependency(VotingNavGraph) {
                val parentEntry = remember(navBackStackEntry) {
                    navController.getBackStackEntry(VotingNavGraph.route)
                }
                hiltViewModel<VotingViewModel>(parentEntry)
            }
        },
    )
}

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
