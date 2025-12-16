package team.aliens.dms.android.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarVisuals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun rememberDmsAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
): DmsAppState {
    return remember(
        navController,
        coroutineScope,
        snackBarHostState,
    ) {
        DmsAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Stable
class DmsAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState,
) {
    private fun showSnackBar(
        visuals: DmsSnackBarVisuals,
        duration: Duration = 2.seconds,
    ) {
        coroutineScope.launch {
            val job = launch {
                snackBarHostState.showSnackbar(visuals)
            }
            delay(duration)
            job.cancel()
        }
    }

    fun showSnackBar(
        snackBarType: DmsSnackBarType,
        message: String,
    ) {
        showSnackBar(
            visuals = DmsSnackBarVisuals(
                snackBarType = snackBarType,
                message = message,
            ),
        )
    }
}
