package team.aliens.dms_android.feature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import team.aliens.design_system.toast.ToastState
import team.aliens.design_system.toast.rememberToastState
import team.aliens.dms_android.feature.common.rememberAvailableFeatures
import team.aliens.domain.model.student.Features

@Composable
internal fun rememberDmsAppState(
    navController: NavHostController = rememberNavController(),
    toastState: ToastState = rememberToastState(),
    availableFeatures: Features = rememberAvailableFeatures(),
) = remember {
    DmsAppState(
        navController = navController,
        toastState = toastState,
        availableFeatures = availableFeatures,
    )
}

// TODO dialog State 관리 로직 추가하기
internal class DmsAppState(
    val navController: NavHostController,
    val toastState: ToastState,
    val availableFeatures: Features,
)
