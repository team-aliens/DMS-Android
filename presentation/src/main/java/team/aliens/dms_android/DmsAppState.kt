package team.aliens.dms_android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.dms_android.util.manager.ToastManager
import team.aliens.domain.model.student.Feature

@Composable
internal fun rememberDmsAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    toastManager: ToastManager = ToastManager,
    availableFeatures: Feature = Feature.falseInitialized(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: MutableState<Boolean> = mutableStateOf(false),
) = remember {
    DmsAppState(
        scaffoldState = scaffoldState,
        navController = navController,
        toastManager = toastManager,
        darkTheme = darkTheme,
        availableFeatures = availableFeatures,
        coroutineScope = coroutineScope,
    )
}

// TODO dialog State 관리 로직 추가하기
internal class DmsAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val toastManager: ToastManager,
    val availableFeatures: Feature,
    val darkTheme: Boolean,
    coroutineScope: CoroutineScope,
) {

    init {
        coroutineScope.launch {
            toastManager.message.collect { toastMessage ->
                if (toastMessage.isNotEmpty()) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = toastMessage[0].message,
                        actionLabel = toastMessage[0].toastType.toString(),
                    )
                }
            }
        }
    }

    @Stable
    val currentDestination = navController.currentDestination?.route

    fun navigateToRoute(
        route: String,
    ) {
        navController.navigate(route)
    }
}

internal fun DmsAppState.navigateToSignIn() {
    this.navController.navigate(DmsRoute.Auth.route) {
        popUpTo(this@navigateToSignIn.navController.currentDestination!!.id) {
            saveState = true
        }
    }
}

internal fun DmsAppState.navigateToHome() {
    this.navController.navigate(DmsRoute.Home.route) {
        launchSingleTop = true
        popUpTo(this@navigateToHome.navController.graph.startDestinationId) {
            saveState = true
        }
    }
}
