package team.aliens.dms.android.feature.voting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@Destination
@Composable
internal fun VotingStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    voteId: UUID,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()

    Text(
        text = "학생 투표",
        style = DmsTheme.typography.body3,
    )
}
