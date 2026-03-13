package team.aliens.dms.android.feature.main.application.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.main.application.ui.Application

@Composable
fun ApplicationRoute(
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateVote: (AllVoteSearch) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    Application(
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = onNavigateOutingApplication,
        onNavigateVolunteerApplication = onNavigateVolunteerApplication,
        onNavigateVote = onNavigateVote,
        onShowSnackBar = onShowSnackBar,
    )
}
