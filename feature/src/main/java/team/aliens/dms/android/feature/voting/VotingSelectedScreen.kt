package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator

@Destination
@Composable
internal fun VotingSelectedScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    votingDetailViewModel: VotingViewModel = hiltViewModel(),
) {
    Text(
        text = "선택 투표",
        style = DmsTheme.typography.body3,
    )
}
