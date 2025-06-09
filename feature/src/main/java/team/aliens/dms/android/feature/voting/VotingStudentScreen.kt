package team.aliens.dms.android.feature.voting

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import team.aliens.dms.android.core.designsystem.ButtonDefaults
import team.aliens.dms.android.core.designsystem.ContainedButton
import team.aliens.dms.android.core.designsystem.DmsTheme
import team.aliens.dms.android.core.designsystem.DmsTopAppBar
import team.aliens.dms.android.core.designsystem.OutlinedButton
import team.aliens.dms.android.core.designsystem.Scaffold
import team.aliens.dms.android.core.designsystem.TextButton
import team.aliens.dms.android.core.ui.PaddingDefaults
import team.aliens.dms.android.core.ui.bottomPadding
import team.aliens.dms.android.core.ui.horizontalPadding
import team.aliens.dms.android.data.voting.model.StudentGcnInfo
import team.aliens.dms.android.feature.R
import team.aliens.dms.android.feature.voting.navigation.VotingNavigator
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun VotingStudentScreen(
    modifier: Modifier = Modifier,
    navigator: VotingNavigator,
    voteOptionId: UUID,
    voteTopicTitle: String,
) {
    val votingDetailViewModel: VotingViewModel = hiltViewModel()
    val uiState by votingDetailViewModel.stateFlow.collectAsStateWithLifecycle()
    var selectedFilter by remember { mutableStateOf("1학년") }
    val filterOptions = listOf(StudentGcnInfo("1학년", 1000), StudentGcnInfo("2학년", 2000), StudentGcnInfo("3학년", 3000))
    var selectedVoteTopicId: UUID? by remember { mutableStateOf(null) }
    val buttonEnabled = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        with(votingDetailViewModel) {
            postIntent(
                intent = VotingIntent.UpdateVotingItem(
                    voteOptionId = voteOptionId,
                ),
            )
            postIntent(
                intent = VotingIntent.UpdateModelStudentStates(
                    grade = 1000,
                ),
            )
        }
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
                    .padding(top = PaddingDefaults.ExtraLarge, bottom = PaddingDefaults.Small),
                text = voteTopicTitle,
                style = DmsTheme.typography.headline3,
            )
            MultiToggleButton(
                currentSelection = selectedFilter,
                toggleStates = filterOptions,
                onToggleChange = { text, grade ->
                    selectedFilter = text
                    votingDetailViewModel.postIntent(
                        intent = VotingIntent.UpdateStudentStates(
                            grade = grade,
                        ),
                    )
                },
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = PaddingDefaults.Large),
            ) {
                items(uiState.filteredStudentList) {
                    StudentProfile(
                        studentGcn = it.gradeClassNumber,
                        name = it.name,
                        profileImageUrl = it.profileImageUrl,
                        isSelected = it.id == selectedVoteTopicId,
                        onClick = {
                            selectedVoteTopicId = it.id
                            votingDetailViewModel.postIntent(
                                intent = VotingIntent.SetVoteTopicId(
                                    voteTopicId = it.id,
                                ),
                            )
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .horizontalPadding()
                    .bottomPadding(),
                onClick = {
                    buttonEnabled.value = false
                    votingDetailViewModel.postIntent(
                        intent = VotingIntent.CreateVoteTable(
                            votingTopicId = voteOptionId,
                            selectedId = uiState.voteTopicId!!,
                        ),
                    )
                    navigator.navigateUp()
                },
                enabled = uiState.voteTopicId != null && buttonEnabled.value,
            ) {
                Text(text = stringResource(R.string.make_vote))
            }
        }
    }
}

@Composable
private fun MultiToggleButton(
    currentSelection: String,
    toggleStates: List<StudentGcnInfo>,
    onToggleChange: (String, Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .horizontalPadding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        toggleStates.forEachIndexed { _, toggleState ->
            val isSelected = currentSelection.equals(toggleState.studentGcn, ignoreCase = true)
            val backgroundColor = if (isSelected) DmsTheme.colorScheme.primary else Color.White
            val textColor = if (isSelected) Color.White else DmsTheme.colorScheme.primary

            OutlinedButton(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(4.dp),
                    ),
                onClick = {
                    onToggleChange(toggleState.studentGcn, toggleState.studentFilterId)
                },
                border = BorderStroke(1.dp, DmsTheme.colorScheme.primary),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(
                    text = toggleState.studentGcn,
                    color = textColor,
                )
            }
        }
    }
}

@Composable
private fun StudentProfile(
    studentGcn: String,
    name: String,
    profileImageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    HorizontalDivider(
        thickness = 1.dp,
        color = DmsTheme.colorScheme.onSurfaceVariant,
    )
    TextButton(
        modifier = Modifier
            .fillMaxWidth(),
        interactionSource = interactionSource,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFC5DCFF) else Color.Unspecified,
        ),
        shape = RectangleShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape),
                model = profileImageUrl,
                contentDescription = "student_image",
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$studentGcn $name",
                textAlign = TextAlign.End,
                color = Color.Black,
            )
        }
    }
}
