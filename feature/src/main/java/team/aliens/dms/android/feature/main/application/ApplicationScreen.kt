package team.aliens.dms.android.feature.main.application

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.RoundedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.clickable
import team.aliens.dms.android.core.designsystem.shadow
import team.aliens.dms.android.core.ui.ExtraLargeVerticalSpace
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.core.ui.topPadding
import team.aliens.dms.android.core.ui.verticalPadding
import team.aliens.dms.android.feature.R
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Destination
@Composable
internal fun ApplicationScreen(
    modifier: Modifier = Modifier,
    onNavigateToRemains: () -> Unit,
    onNavigateToOuting: () -> Unit,
    onNavigateToVolunteers: () -> Unit,
    onNavigateToModelStudent: (voteOptionId: UUID, voteTopicTitle: String) -> Unit,
    onNavigateToApprovalVote: (voteOptionId: UUID, voteTopicTitle: String) -> Unit,
    onNavigateToStudentVote: (voteOptionId: UUID, voteTopicTitle: String) -> Unit,
    onNavigateToSelectedVote: (voteOptionId: UUID, voteTopicTitle: String) -> Unit,
) {
    val viewModel: ApplicationViewModel = hiltViewModel()
    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("신청", "투표")

    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    Scaffold(
        modifier = modifier,
        topBar = {
            DmsTopAppBar(
                title = {
                    Column(
                        modifier = Modifier,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clickable { selectedTab = index },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = title,
                                        style = DmsTheme.typography.body2,
                                        color = DmsTheme.colorScheme.onBackground,
                                    )
                                    if (selectedTab == index) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(2.dp)
                                                .background(if (selectedTab == index) DmsTheme.colorScheme.onBackground else DmsTheme.colorScheme.line)
                                                .align(Alignment.BottomCenter),
                                        )
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFE5E5E5)),
                        )
                    }
                },
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .bottomPadding(100.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                ) {
                    when (selectedTab) {
                        0 -> {
                            item {
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .topPadding(),
                                    title = stringResource(id = R.string.volunteers_application),
                                    description = stringResource(id = R.string.volunteers_description),
                                    buttonText = stringResource(id = R.string.volunteers_do_description),
                                    onButtonClick = onNavigateToVolunteers,
                                )
                            }
                        }
                        1 -> {
                            if (uiState.studentVoteList.isEmpty() && uiState.modelStudentVoteList.isEmpty() && uiState.approvalVoteList.isEmpty() && uiState.selectedVoteList.isEmpty()) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillParentMaxHeight(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = stringResource(R.string.not_vote_time),
                                            style = DmsTheme.typography.body2,
                                            textAlign = TextAlign.Center,
                                        )
                                    }
                                }
                            } else {
                                items(uiState.modelStudentVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        isVoted = it.isVoted,
                                        onButtonClick = { onNavigateToModelStudent(it.id, it.topicName) },
                                    )
                                }
                                items(uiState.selectedVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        isVoted = it.isVoted,
                                        onButtonClick = { onNavigateToSelectedVote(it.id, it.topicName) },
                                    )
                                }
                                items(uiState.studentVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        isVoted = it.isVoted,
                                        onButtonClick = { onNavigateToStudentVote(it.id, it.topicName) },
                                    )
                                }
                                items(uiState.approvalVoteList) {
                                    VoteCard(
                                        topStartTimeTitle = it.startTime,
                                        topEndTimeTitle = it.endTime,
                                        title = it.topicName,
                                        description = it.description,
                                        isVoted = it.isVoted,
                                        onButtonClick = { onNavigateToApprovalVote(it.id, it.topicName) },
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
    isVoted: Boolean,
    onButtonClick: () -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
    val buttonText = if (isVoted) "투표 종료" else "투표하기"

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
                enabled = !isVoted,
            ) {
                Text(text = buttonText)
            }
        }
    }
}
