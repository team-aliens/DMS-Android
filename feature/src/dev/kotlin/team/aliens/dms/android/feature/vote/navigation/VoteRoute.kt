package team.aliens.dms.android.feature.vote.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.vote.ui.Vote

@Composable
fun VoteRoute(
    title: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Vote(
        title = title,
        onShowSnackBar = onShowSnackBar,
        onNavigateBack = onNavigateBack,
    )
}
