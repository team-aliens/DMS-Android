package team.aliens.dms_android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.toast.ToastState
import team.aliens.design_system.toast.rememberToastState
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.domain.model.student.Feature

@Composable
internal fun rememberDmsAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    toastState: ToastState = rememberToastState(),
    availableFeatures: Feature = Feature.falseInitialized(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    bottomSheetState: MutableState<Boolean> = mutableStateOf(false),
) = remember {
    DmsAppState(
        scaffoldState = scaffoldState,
        navController = navController,
        toastState = toastState,
        darkTheme = darkTheme,
        availableFeatures = availableFeatures,
    )
}

// TODO dialog State 관리 로직 추가하기
internal class DmsAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val toastState: ToastState,
    val availableFeatures: Feature,
    val darkTheme: Boolean,
)

internal fun NavHostController.navigateToSignIn() {
    this.navigate(DmsRoute.Auth.route) {
        popUpTo(this@navigateToSignIn.graph.id) {
            inclusive = true
        }
    }
}

internal fun NavHostController.navigateToHome() {
    this.navigate(DmsRoute.Home.route) {
        launchSingleTop = true
        popUpTo(this@navigateToHome.graph.startDestinationId) {
            saveState = true
        }
    }
}
