package team.aliens.dms.android.feature.vote.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.vote.ui.Vote
import java.util.UUID

@Composable
fun VoteRoute(
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Vote(
        onShowSnackBar = onShowSnackBar,
        onNavigateBack = onNavigateBack,
    )
}
