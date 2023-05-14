package team.aliens.dms_android.feature.application

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import team.aliens.design_system.toast.ToastType

@Composable
internal fun rememberDmsAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    DmsAppState(
        snackbarHostState = scaffoldState.snackbarHostState,
        navController = navController,
        coroutineScope = coroutineScope,
    )
}

internal class DmsAppState(
    private val snackbarHostState: SnackbarHostState,
    private val navController: NavController,
    private val coroutineScope: CoroutineScope,
) {

    @Stable
    val dialogState
        @Composable get() = remember { mutableStateOf(false) }

    @Stable
    val currentDestination = navController.currentDestination?.route

    internal fun showToast(
        message: String,
        toastType: ToastType,
    ) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = toastType.toString(),
            )
        }
    }

    internal fun navigateToRoute(
        route: String,
    ) {
        navController.navigate(route)
    }

    @Composable
    internal fun setDialogState(
        dialogState: Boolean,
    ) {
        this.dialogState.value = dialogState
    }
}
