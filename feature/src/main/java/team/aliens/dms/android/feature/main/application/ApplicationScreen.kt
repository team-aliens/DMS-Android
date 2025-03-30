package team.aliens.dms.android.feature.main.application

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.DefaultVerticalSpace
import team.aliens.dms.android.core.ui.ExtraLargeVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.feature.R
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToStudyRoomList: () -> Unit,
    onNavigateToRemains: () -> Unit,
    onNavigateToOuting: () -> Unit,
    onNavigateToModelStudent: (voteOption: AllVoteSearch) -> Unit,
    onNavigateToApprovalVote: (voteOption: AllVoteSearch) -> Unit,
    onNavigateToStudentVote: (voteOption: AllVoteSearch) -> Unit,
    onNavigateToSelectedVote: (voteOption: AllVoteSearch) -> Unit,
) {
    val viewModel: ApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { 2 })

    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Text(
                        text = when(pagerState.currentPage) {
                            0 -> stringResource(R.string.application)
                            else -> stringResource(R.string.voting_submit)
                        },
                        style = DmsTheme.typography.body2
                    )
                }
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            HorizontalPager(
                modifier = modifier
                    .fillMaxSize()
                    .align(Alignment.Start),
                state = pagerState,
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .bottomPadding(100.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(2) { index ->
                            val color = if (pagerState.currentPage == index)
                                DmsTheme.colorScheme.backgroundVariant
                            else
                                DmsTheme.colorScheme.onSurfaceVariant
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(8.dp)
                            )
                        }
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                    ) {
                        when (page) {
                            0 -> {
                                items(1) {
                                    ApplicationCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .topPadding(),
                                        title = stringResource(id = R.string.study_room_application),
                                        appliedTitle = uiState.appliedStudyRoom?.let { studyRoom ->
                                            stringResource(
                                                id = R.string.format_study_room_applied_text,
                                                studyRoom.floor,
                                                studyRoom.name,
                                            )
                                        },
                                        description = stringResource(id = R.string.study_room_description),
                                        buttonText = stringResource(id = R.string.study_room_do_application),
                                        onButtonClick = onNavigateToStudyRoomList,
                                    )
                                    ApplicationCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        title = stringResource(id = R.string.remains_application),
                                        appliedTitle = uiState.appliedRemainsOption?.title,
                                        description = stringResource(id = R.string.remains_description),
                                        buttonText = stringResource(id = R.string.remains_do_application),
                                        onButtonClick = onNavigateToRemains,
                                    )
                                    ApplicationCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        title = stringResource(id = R.string.outing_application),
                                        description = stringResource(id = R.string.outing_description),
                                        buttonText = stringResource(id = R.string.outing_do_application),
                                        onButtonClick = onNavigateToOuting,
                                    )
                                    ApplicationCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        title = stringResource(id = R.string.outing_application),
                                        description = stringResource(id = R.string.outing_description),
                                        buttonText = stringResource(id = R.string.outing_do_application),
                                        onButtonClick = onNavigateToOuting,
                                    )
                                }
                            }
                            1 -> {
                                items(uiState.modelStudentVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        onButtonClick = {

                                        },
                                    )
                                }
                                items(uiState.selectedVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        onButtonClick = {
                                            Log.d("TEST", it.id.toString())
                                            onNavigateToSelectedVote(it) },
                                    )
                                }
                                items(uiState.studentVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        onButtonClick = { onNavigateToStudentVote(it) },
                                    )
                                }
                                items(uiState.approvalVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        onButtonClick = { onNavigateToApprovalVote(it) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    appliedTitle: String? = null,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
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
            verticalArrangement = Arrangement.spacedBy(ExtraLargeVerticalSpace),
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
                    .bottomPadding(28.dp),
                onClick = onButtonClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}


@Composable
private fun VoteCard(
    modifier: Modifier = Modifier,
    topStartTimeTitle: LocalDateTime,
    topEndTimeTitle: LocalDateTime,
    title: String,
    description: String,
    appliedTitle: String? = null,
    buttonText: String = "투표하기",
    onButtonClick: () -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")

    Text(
        modifier = modifier
            .horizontalPadding(),
        text = "${topStartTimeTitle.format(formatter)} ~ ${topEndTimeTitle.format(formatter)}",
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
            verticalArrangement = Arrangement.spacedBy(ExtraLargeVerticalSpace),
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
                    .bottomPadding(28.dp),
                onClick = onButtonClick,
            ) {
                Text(text = buttonText)
            }
        }
    }
}
