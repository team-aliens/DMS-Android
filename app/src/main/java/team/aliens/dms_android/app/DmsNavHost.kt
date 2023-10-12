package team.aliens.dms_android.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.utils.navGraph
import team.aliens.dms_android.app.navigation.DmsNavGraph
import team.aliens.dms_android.app.navigation.DmsNavigator

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
        dependenciesContainerBuilder = {
            dependency(
                DmsNavigator(
                    navGraph = navBackStackEntry.navGraph(),
                    navController = navController,
                )
            )
        },
    )
}
