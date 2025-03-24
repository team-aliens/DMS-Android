package team.aliens.dms.android.feature.voting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator

@Destination
@Composable
fun VotingModelStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    votingDetailViewModel: VotingViewModel = hiltViewModel(),
) {
}
