package team.aliens.dms.android.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScopeWithNoDependencies
import com.ramcosta.composedestinations.spec.NavGraphSpec

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
    autoSignIn: Boolean,
) {
    val engine = rememberAnimatedNavHostEngine()
    val navController = engine.rememberNavController()

    DestinationsNavHost(
        modifier = modifier,
        engine = engine,
        navGraph = DmsNavGraph.root(autoSignIn = autoSignIn),
        navController = navController,
        dependenciesContainerBuilder = {
            dependency(currentNavigator(autoSignIn = autoSignIn))
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
