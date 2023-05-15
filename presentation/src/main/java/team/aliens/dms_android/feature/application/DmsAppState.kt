package team.aliens.dms_android.feature.application

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import team.aliens.dms_android.ToastManager

@Composable
internal fun rememberDmsAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController(),
    toastManager: ToastManager = ToastManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: MutableState<Boolean> = mutableStateOf(false),
) = remember {
    DmsAppState(
        scaffoldState = scaffoldState,
        navController = navController,
        toastManager = toastManager,
        coroutineScope = coroutineScope,
    )
}

internal class DmsAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavController,
    val toastManager: ToastManager,
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

    /*init{
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = toastType.toString(),
            )
        }
    }

    fun showToast(
        message: String,
        toastType: ToastType,
    ) {

    }*/

    fun navigateToRoute(
        route: String,
    ) {
        navController.navigate(route)
    }
}
