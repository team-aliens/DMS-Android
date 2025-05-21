package team.aliens.dms.android.feature.voting

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingApprovalScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    voteOptionId: UUID,
    voteTopicTitle: String,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()
    val approvalIdList: MutableList<UUID> = mutableListOf()
    var approvalTopicId: UUID? by remember { mutableStateOf(null) }

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
        containerColor = Color.White,
    ) { padValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Text(
                modifier = modifier
                    .horizontalPadding()
                    .padding(top = PaddingDefaults.ExtraLarge),
                text = voteTopicTitle,
                style = DmsTheme.typography.headline3,
            )
            LazyColumn(
                modifier = modifier
                    .padding(top = PaddingDefaults.Small),
            ) {
                items(uiState.votingTopicCheckList) {
                    approvalIdList.add(it.id)
                }
            }
            Row(
                modifier = modifier
                    .horizontalPadding()
                    .padding(top = 128.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),

            ) {
                ApprovalCard(
                    modifier = Modifier.weight(1f),
                    imageModelUrl = team.aliens.dms.android.core.designsystem.R.drawable.ic_circle_outline,
                    contentName = "",
                    isSelected = approvalIdList.any { approvalTopicId == approvalIdList.component1() },
                    selectedColor = 0xFFC5DCFF,
                    onClick = {
                        approvalTopicId = approvalIdList.component1()
                        votingDetailViewModel.postIntent(
                            intent = VotingIntent.SetVoteTopicId(
                                voteTopicId = approvalIdList.component1(),
                            ),
                        )
                    },
                )
                ApprovalCard(
                    modifier = Modifier.weight(1f),
                    imageModelUrl = team.aliens.dms.android.core.designsystem.R.drawable.ic_wrong,
                    contentName = "",
                    isSelected = approvalIdList.any { approvalTopicId == approvalIdList.component2() },
                    selectedColor = 0xFFFFC3C3,
                    onClick = {
                        approvalTopicId = approvalIdList.component2()
                        votingDetailViewModel.postIntent(
                            intent = VotingIntent.SetVoteTopicId(
                                voteTopicId = approvalIdList.component2(),
                            ),
                        )
                    },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {
                    votingDetailViewModel.postIntent(
                        intent = VotingIntent.CreateVoteTable(
                            votingTopicId = voteOptionId,
                            selectedId = uiState.voteTopicId!!,
                        ),
                    )
                    navigator.navigateUp()
                },
                enabled = uiState.voteTopicId != null,
            ) {
                Text(text = "투표하기")
            }
        }
    }
}

@Composable
fun ApprovalCard(
    modifier: Modifier = Modifier,
    @DrawableRes imageModelUrl: Int,
    contentName: String,
    isSelected: Boolean,
    selectedColor: Long,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFDEDEDE),
                shape = RoundedCornerShape(12.dp),
            )
            .clip(RoundedCornerShape(12.dp))
            .background(color = if (isSelected) Color(selectedColor) else Color.White)
            .clickable {
                onClick()
            },
    ) {
        Image(
            modifier = modifier
                .padding(
                    vertical = 60.dp,
                    horizontal = 42.dp,
                ),
            painter = painterResource(imageModelUrl),
            contentDescription = contentName,
        )
    }
}
