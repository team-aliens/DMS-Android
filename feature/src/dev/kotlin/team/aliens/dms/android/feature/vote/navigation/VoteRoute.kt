package team.aliens.dms.android.feature.vote.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.feature.vote.ui.Vote
import java.time.LocalDateTime

@Composable
fun VoteRoute(
    title: String,
    startTime: String,
    endTime: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Vote(
        title = title,
        startTime = startTime,
        endTime = endTime,
        onShowSnackBar = onShowSnackBar,
        onNavigateBack = onNavigateBack,
    )
}
