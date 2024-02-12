package team.aliens.dms.android.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
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
import team.aliens.dms.android.app.navigation.DmsNavGraph
import team.aliens.dms.android.app.navigation.DmsNavigator
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph

@Composable
fun DmsApp(
    windowSizeClass: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    engine: NavHostEngine = rememberDmsNavHostEngine(),
    appState: DmsAppState = rememberDmsAppState(
        navController = engine.rememberNavController(),
    ),
) {
    DestinationsNavHost(
        engine = engine,
        navGraph = DmsNavGraph.root(autoSignIn = false), // FIXME: auto sign in
        navController = appState.navController,
        dependenciesContainerBuilder = {
            dependency(currentNavigator(autoSignIn = false)) // FIXME: auto sign in
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
        ),
        UnauthorizedNavGraph to NestedNavGraphDefaultAnimations(
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
