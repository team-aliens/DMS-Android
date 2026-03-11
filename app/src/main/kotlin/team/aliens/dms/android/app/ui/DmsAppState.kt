package team.aliens.dms.android.app.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarVisuals
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun rememberDmsAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
): DmsAppState {
    return remember(
        coroutineScope,
        snackBarHostState,
    ) {
        DmsAppState(
            coroutineScope = coroutineScope,
            snackBarHostState = snackBarHostState,
        )
    }
}

@Stable
class DmsAppState(
    val coroutineScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState,
) {
    private fun showSnackBar(
        visuals: DmsSnackBarVisuals,
        duration: Duration = 2.seconds,
    ) {
        coroutineScope.launch {
            withTimeoutOrNull(duration) {
                snackBarHostState.showSnackbar(visuals)
            }
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
