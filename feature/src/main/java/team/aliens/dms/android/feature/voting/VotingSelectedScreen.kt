package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator

@Destination
@Composable
fun VotingSelectedScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    votingDetailViewModel: VotingViewModel = hiltViewModel()
) {
    Log.d("TEST", "${votingDetailViewModel.stateFlow.collectAsState().value.selectedVoteList}")
}
