package team.aliens.dms.android.feature.latestudy.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.latestudy.ui.LateStudyDetailScreen

@Composable
fun LateStudyDetailRoute(
    onBack: () -> Unit,
    onCancelled: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    LateStudyDetailScreen(
        onBack = onBack,
        onCancelled = onCancelled,
        onShowSnackBar = onShowSnackBar,
    )
}
