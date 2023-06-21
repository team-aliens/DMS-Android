package team.aliens.dms_android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.toast.ToastState
import team.aliens.design_system.toast.rememberToastState
import team.aliens.dms_android.common.rememberAvailableFeatures
import team.aliens.dms_android.feature.DmsRoute
import team.aliens.domain.model.student.Feature

@Composable
internal fun rememberDmsAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    toastState: ToastState = rememberToastState(),
    availableFeatures: Feature = rememberAvailableFeatures(),
    darkTheme: Boolean = isSystemInDarkTheme(),
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
