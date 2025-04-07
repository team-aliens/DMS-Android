package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonColors
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingSelectedScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    voteOptionId: UUID,
    voteTopicTitle: String,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        votingDetailViewModel.updateCheckVotingItem(voteOptionId = voteOptionId)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            modifier = modifier
                                .padding(end = 40.dp),
                            text = stringResource(R.string.voting_submit),
                            style = DmsTheme.typography.body2,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(team.aliens.dms.android.core.designsystem.R.drawable.chevronleft),
                            contentDescription = stringResource(R.string.voting_submit),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padValues)
                .horizontalPadding(),
        ) {
            Text(
                modifier = Modifier
                    .horizontalPadding()
                    .padding(top = PaddingDefaults.ExtraLarge),
                text = voteTopicTitle,
                style = DmsTheme.typography.headline3,
            )
            LazyColumn(
                modifier = modifier
                    .verticalPadding(PaddingDefaults.Small)
            ) {
                items(uiState.votingTopicCheckList) {

                }
            }
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = navigator::navigateUp,
            ) {
                Text(text = "투표하기")
            }
        }
    }
}
