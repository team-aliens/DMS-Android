package team.aliens.dms.android.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine
import team.aliens.dms.android.app.navigation.authorized.AuthorizedNavGraph
import team.aliens.dms.android.app.navigation.unauthorized.UnauthorizedNavGraph

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun rememberDmsAppState(
    engine: NavHostEngine = rememberAnimatedNavHostEngine(
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
            ),
        ),
    ),
    navController: NavHostController = engine.rememberNavController(),
    context: Context = LocalContext.current,
) = remember(navController, context) {
    DmsAppState(
        navController = navController,
        context = context,
    )
}

class DmsAppState(
    val navController: NavHostController,
    private val context: Context,
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    /**
     * copied from jetcaster, android compose samples
     * https://github.com/android/compose-samples/blob/main/Jetcaster/app/src/main/java/com/example/jetcaster/ui/JetcasterAppState.kt
     */
    private fun checkIfOnline(): Boolean {
        val cm = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

        val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }

    fun refreshOnline() {
        this.isOnline = checkIfOnline()
    }
}
