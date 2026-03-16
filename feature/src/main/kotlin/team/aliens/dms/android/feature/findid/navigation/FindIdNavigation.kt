package team.aliens.dms.android.feature.findid.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.findid.FindIdScreen

@Composable
fun FindIdRoute(
    onNavigateToBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    FindIdScreen(
        onNavigateToBack = onNavigateToBack,
        onShowSnackBar = onShowSnackBar,
    )
}
