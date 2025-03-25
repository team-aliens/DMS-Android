package team.aliens.dms.android.feature.voting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.collectInLaunchedEffectWithLifecycle
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
internal fun VotingScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    viewModel: VotingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { 2 })

    viewModel.sideEffectFlow.collectInLaunchedEffectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is VotingSideEffect.MoveToVoteDetail -> {
                when (sideEffect.voteOption.voteType) {
                    Vote.MODEL_STUDENT_VOTE -> navigator.openVotingModelStudent()
                    Vote.STUDENT_VOTE -> navigator.openVotingStudent()
                    Vote.OPTION_VOTE -> navigator.openVotingSelected()
                    Vote.APPROVAL_VOTE -> navigator.openVotingApproval()
                    else -> {
                        // 처리 해야 할 작업 없음
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.voting_submit))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigator::navigateUp) {
                        Icon(
                            painter = painterResource(id = team.aliens.dms.android.core.designsystem.R.drawable.chevronleft),
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
                .padding(padValues),
        ) {
            HorizontalPager(
                state = pagerState,
            ) { _ ->
                repeat(pagerState.pageCount) {
                    val color = if (pagerState.currentPage == it)
                        DmsTheme.colorScheme.backgroundVariant
                    else
                        DmsTheme.colorScheme.background
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp),
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .horizontalPadding(),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                ) {
                    items(uiState.modelStudentVoteList) {
                        VoteCard(
                            topStartTimeTitle = it.startTime,
                            topEndTimeTitle = it.endTime,
                            title = it.topicName,
                            description = it.description,
                            onButtonClick = navigator::openVotingModelStudent,
                        )
                    }
                    items(uiState.selectedVoteList) {
                        VoteCard(
                            topStartTimeTitle = it.startTime,
                            topEndTimeTitle = it.endTime,
                            title = it.topicName,
                            description = it.description,
                            onButtonClick = navigator::openVotingSelected,
                        )
                    }
                    items(uiState.studentVoteList) {
                        VoteCard(
                            topStartTimeTitle = it.startTime,
                            topEndTimeTitle = it.endTime,
                            title = it.topicName,
                            description = it.description,
                            onButtonClick = navigator::openVotingStudent,
                        )
                    }
                    items(uiState.approvalVoteList) {
                        VoteCard(
                            topStartTimeTitle = it.startTime,
                            topEndTimeTitle = it.endTime,
                            title = it.topicName,
                            description = it.description,
                            onButtonClick = {
                                viewModel.postIntent(
                                    VotingIntent.UpdateVotingItem(
                                        it,
                                    ),
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VoteCard(
    modifier: Modifier = Modifier,
    topStartTimeTitle: LocalDate,
    topEndTimeTitle: LocalDate,
    title: String,
    description: String,
    appliedTitle: String? = null,
    buttonText: String = "투표하기",
    onButtonClick: () -> Unit,
) {
    Text(
        text = "$topStartTimeTitle ~ $topEndTimeTitle",
        style = DmsTheme.typography.body2,
        color = DmsTheme.colorScheme.primary,
    )
    Card(
        modifier = modifier
            .animateContentSize()
            .horizontalPadding()
            .verticalPadding()
            .shadow(),
        shape = DmsTheme.shapes.surfaceSmall,
        colors = CardDefaults.elevatedCardColors(
            containerColor = DmsTheme.colorScheme.surface,
            contentColor = DmsTheme.colorScheme.onSurface,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(DefaultVerticalSpace),
        ) {
            Row(
                modifier = Modifier
                    .animateContentSize()
                    .horizontalPadding()
                    .topPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    color = DmsTheme.colorScheme.onSurface,
                    style = DmsTheme.typography.title2,
                )
                AnimatedVisibility(
                    visible = appliedTitle != null,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut(),
                ) {
                    if (appliedTitle != null) {
                        RoundedButton(
                            onClick = { },
                            fillMinSize = false,
                            contentPadding = PaddingValues(
                                horizontal = PaddingDefaults.Medium,
                                vertical = PaddingDefaults.Small,
                            ),
                        ) {
                            Text(text = appliedTitle)
                        }
                    }
                }
            }
            Text(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding(),
                text = description,
                style = DmsTheme.typography.body3,
                color = DmsTheme.colorScheme.onSurface,
            )
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = onButtonClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}
