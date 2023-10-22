package team.aliens.dms.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScopeWithNoDependencies
import com.ramcosta.composedestinations.spec.NavGraphSpec
import dagger.hilt.android.AndroidEntryPoint
import team.aliens.dms.android.app.navigation.DmsNavGraph
import team.aliens.dms.android.app.navigation.DmsNavigator
import team.aliens.dms.android.core.designsystem.DmsScaffold
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.ToastLayout
import team.aliens.dms.android.core.designsystem.rememberToastState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val autoSignIn by viewModel.autoSignInAvailable.collectAsStateWithLifecycle()
            val toast = rememberToastState()

            DmsTheme {
                ToastLayout(
                    modifier = Modifier.fillMaxSize(),
                    toastState = toast,
                ) {
                    DmsScaffold {
                        DmsNavHost(
                            modifier = Modifier.fillMaxSize(),
                            autoSignIn = autoSignIn,
                        )
                    }
                }
            }
        }
    }

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
}
