package team.aliens.dms_android.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import team.aliens.dms_android.app.navigation.DmsNavGraph

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun DmsNavHost(
    modifier: Modifier = Modifier,
    autoSignIn: Boolean,
) {
    val engine = rememberAnimatedNavHostEngine()

    DestinationsNavHost(
        engine = engine,
        modifier = modifier,
        navGraph = DmsNavGraph.root(autoSignIn = autoSignIn),
    )
}
